package com.neighborly.neighborlyandroid.networking


import TokenDataStore
import com.neighborly.neighborlyandroid.login.models.LoginRequest
import com.neighborly.neighborlyandroid.login.models.LoginResponse
import com.neighborly.neighborlyandroid.login.models.RegisterResponse

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService{
    @Headers(
        "Connection: keep-alive",
        "Content-Type: application/json"
    )
    @POST("login/")
    suspend fun login(@Body request: LoginRequest):Response<LoginResponse>
    @POST("/register/")
    suspend fun register(): RegisterResponse


}

class ApiServiceImpl(
    private val tokenDataStore: TokenDataStore
) : ApiService {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/"
    }

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    @Headers(
        "Connection: keep-alive",
        "Content-Type: application/json"
    )
    @POST("login/")
    override suspend fun login(@Body request: LoginRequest): Response<LoginResponse> {
        val response: Response<LoginResponse> = apiService.login(request)
        // it deals with storage of token in TokenDataStore
        if (response.isSuccessful){
            response.body()?.data?.let { tokenDataStore.saveToken(it.Token) }
        }
        //check if response is OK
        return response
    }

    @POST("/register/")
    override suspend fun register(): RegisterResponse {
        return apiService.register()
    }
}
//val BASE_URL:String =  "http://10.0.2.2:8000/"
//
//
//val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//val okHttpClient = OkHttpClient.Builder()
//    .addInterceptor(loggingInterceptor)
//    .build()
//private val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(
//    okHttpClient).build()
//val apiService= retrofit.create(ApiService::class.java)

