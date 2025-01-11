package com.neighborly.neighborlyandroid.domain.repository

import android.util.Log
import com.google.gson.Gson
import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import com.neighborly.neighborlyandroid.common.LoginResponseState
import com.neighborly.neighborlyandroid.data.network.dto.ApiErrorResponse
import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginRequest
import com.neighborly.neighborlyandroid.data.network.retrofit.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class LoginRepository(private val apiService: LoginService,
                      private val tokenDataStore: TokenDataStore) {
    suspend fun login(email:String, password:String): LoginResponseState = withContext(Dispatchers.IO){
        //Network and local storage IO operations should be done in IO Context

        try {
            val response = apiService.login(LoginRequest(email,password))
            if (response.isSuccessful) {

                val successResponse = response.body()
                val token:String = successResponse?.Token ?: ""
                runBlocking {
                    tokenDataStore.saveToken(token)

                }
                Log.i("logs","Login Succesful, auth token stored : "+token)
                LoginResponseState.Success
            }else{
                val apiError = Gson().fromJson(
                    response.errorBody()!!.string(),
                    ApiErrorResponse::class.java
                )
                if (apiError.type == "client_error" && apiError.errors[0].code == "not_authenticated"){
                    LoginResponseState.Error.AccessDenied
                }else{
                    LoginResponseState.Error.ServerError
                }
            }
        }catch (e:Exception){
            e.message?.let { Log.d("logs", "LoginRepository Error : " + it) }
            LoginResponseState.Error.ServerError
        }



    }
}