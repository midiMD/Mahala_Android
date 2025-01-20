package com.neighborly.neighborlyandroid.data.network.retrofit



import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginApiResponse
import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.PasswordResetApiResponse
import com.neighborly.neighborlyandroid.data.network.dto.authentication.PasswordResetRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.RegisterRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.RegisterResponse
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
    suspend fun login(@Body request: LoginRequest): Response<LoginApiResponse.Success>
    @POST("password-reset/")
    suspend fun passwordReset(@Body request: PasswordResetRequest): Response<Unit>
    @POST("register/")
    suspend fun register(@Body request: RegisterRequest):Response<Unit>
}
