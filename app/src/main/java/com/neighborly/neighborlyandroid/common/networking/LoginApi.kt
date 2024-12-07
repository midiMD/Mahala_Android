package com.neighborly.neighborlyandroid.common.networking

import com.neighborly.neighborlyandroid.login.models.LoginRequest
import com.neighborly.neighborlyandroid.login.models.LoginResponse
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiRequest
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi{
    @Headers(
        "Connection: keep-alive",
        "Content-Type: application/json"
    )
    @POST("login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @POST("/register/")
    suspend fun register(@Body request: UserRegisterApiRequest): Response<UserRegisterApiResponse.Success>
}
