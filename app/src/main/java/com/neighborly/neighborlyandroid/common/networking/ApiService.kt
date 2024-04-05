package com.neighborly.neighborlyandroid.common.networking


import TokenDataStore
import android.util.Log
import com.google.gson.Gson
import com.neighborly.neighborlyandroid.login.models.LoginRequest
import com.neighborly.neighborlyandroid.login.models.LoginResponse

import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiRequest
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiResponse

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.HttpException


interface UnAuthApiService{
    @Headers(
        "Connection: keep-alive",
        "Content-Type: application/json"
    )
    @POST("login/")
    suspend fun login(@Body request: LoginRequest):Response<LoginResponse>
    @POST("/register/")
    suspend fun register(@Body request: UserRegisterApiRequest): Response<UserRegisterApiResponse.Success>
}


class UnAuthApiServiceImpl(
    private val tokenDataStore: TokenDataStore
) : UnAuthApiService {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/"
    }

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val apiService = retrofit.create(UnAuthApiService::class.java)

    @Headers(
        "Connection: keep-alive",
        "Content-Type: application/json"
    )
    @POST("login/")
    override suspend fun login(@Body request: LoginRequest): Response<LoginResponse> {
        val response: Response<LoginResponse> = apiService.login(request)
        // it deals with storage of token in TokenDataStore
        if (response.isSuccessful) {
            response.body()?.data?.let { tokenDataStore.saveToken(it.Token) }
        }
        return response
    }

    @POST("register/")
    override suspend fun register(@Body request: UserRegisterApiRequest): Response<UserRegisterApiResponse.Success> {
        val response = apiService.register(request)

        if (response.isSuccessful) {
            tokenDataStore.saveToken(response.body()!!.data.Token)
        }
        return response
    }
}

