package com.neighborly.neighborlyandroid.login.data

import android.util.Log
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.common.networking.LoginService
import com.neighborly.neighborlyandroid.login.models.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LoginRepository(private val apiService: LoginService) {
    suspend fun makeLoginRequest(requestBody: LoginRequest): Boolean = withContext(Dispatchers.IO){
        //Network and local storage IO operations should be done in IO Context
        var isAuth: Boolean = false
        try {
            val response = apiService.login(requestBody)
            when (response.code()){
                200 -> isAuth = true
                404 -> isAuth=false
            }

            response.body()?.let { Log.d("token", it.Token) }

        }catch (e:Exception){
            e.message?.let { Log.d("LoginViewModelError", it) }
        }


        isAuth // no "return" because we are using withContext
    }
}