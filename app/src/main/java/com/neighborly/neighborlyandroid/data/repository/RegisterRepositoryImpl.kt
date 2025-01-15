package com.neighborly.neighborlyandroid.data.repository
import android.util.Log
import com.google.gson.Gson
import com.neighborly.neighborlyandroid.common.RegisterResponseState
import com.neighborly.neighborlyandroid.data.network.dto.ApiErrorResponse
import com.neighborly.neighborlyandroid.data.network.dto.authentication.RegisterRequest
import com.neighborly.neighborlyandroid.data.network.retrofit.LoginService
import com.neighborly.neighborlyandroid.domain.model.RegistrationDetails
import com.neighborly.neighborlyandroid.domain.repository.RegisterRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class RegisterRepositoryImpl(private val apiService: LoginService): RegisterRepository {
    override suspend fun register(details: RegistrationDetails): RegisterResponseState = withContext(Dispatchers.IO){
        //Network and local storage IO operations should be done in IO Context
        //var userRegisterApiResponseState: RegisterApiResponseState = RegisterApiResponseState()
        try {
            val registerRequestData: RegisterRequest = RegisterRequest.from(details)
            val response = apiService.register(registerRequestData)

            if(response.isSuccessful){
                Log.d("logs","RegisterRepo success registration")
                RegisterResponseState.Success

            } else if (response.code() == 500){
                //internal server error
                RegisterResponseState.Error.ServerError
            } else {
                val apiError = Gson().fromJson(
                    response.errorBody()!!.string(),
                    ApiErrorResponse::class.java
                )

                if (apiError.errors[0].code=="unique" && apiError.errors[0].attr=="email"){
                    RegisterResponseState.Error.EmailExists
                }else{
                    RegisterResponseState.Error.ServerError

                }
            }

        }catch (e:Exception){
            throw e
//            e.message?.let { Log.d("RegisterRepositoryError", it) }
//            RegisterResponseState.Error.ClientError

        }

    }
}