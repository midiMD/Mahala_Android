package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginResponse
import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.PasswordResetRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.RegisterRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.ValidateAuthTokenResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// TODO:
//
class LoginService(private val tokenDataStore: TokenDataStore
) : LoginApi {

    private val retrofit = RetrofitClient().getClient()
    private val loginApi = retrofit.create(LoginApi::class.java)

    @Headers(
        "Connection: keep-alive",
        "Content-Type: application/json"
    )
    @POST("login/")
    override suspend fun login(@Body request: LoginRequest): Response<LoginResponse> {
        Log.d("logs","login request with: " + request.toString())
        val response= loginApi.login(request)
        Log.d("logs","Login api response " + response.toString())
        return response
        // it deals with storage of token in TokenDataStore

    }
    @POST("password-reset/")
    override suspend fun passwordReset(@Body request: PasswordResetRequest): Response<Unit> {
        Log.d("logs","Password reset request with: " + request.toString())
        val response= loginApi.passwordReset(request)
        return response

    }


    @POST("register/")
    override suspend fun register(@Body request: RegisterRequest):Response<Unit> {
        val response = loginApi.register(request)

        return response
    }



    @POST("auth/")
    override suspend fun validateAuthToken(): Response<ValidateAuthTokenResponse> {
        val token = tokenDataStore.getToken() // Directly assign the result
        val retrofit = AuthRetrofitClient(token= token ).getClient()
        val api = retrofit.create(LoginApi::class.java)
        val response = api.validateAuthToken()
        return response
    }

}

