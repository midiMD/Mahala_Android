package com.neighborly.neighborlyandroid.common.networking


import TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.login.models.LoginRequest
import com.neighborly.neighborlyandroid.login.models.LoginResponse

import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiRequest
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiResponse
import kotlinx.coroutines.runBlocking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class LoginService(
    private val tokenDataStore: TokenDataStore
) : LoginApi {
//    companion object {
//        private const val BASE_URL = "http://10.0.2.2:8000/"
//    }
//
//    private val loggingInterceptor =
//        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor(loggingInterceptor)
//        .build()
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(okHttpClient)
//        .build()
//
//    private val apiService = retrofit.create(UnAuthApiService::class.java)
    private val retrofit = RetrofitClient().getClient()
    private val loginApi = retrofit.create(LoginApi::class.java)

    @Headers(
        "Connection: keep-alive",
        "Content-Type: application/json"
    )
    @POST("login/")
    override suspend fun login(@Body request: LoginRequest): Response<LoginResponse> {
        val response: Response<LoginResponse> = loginApi.login(request)
        // it deals with storage of token in TokenDataStore
        if (response.isSuccessful) {
            val token:String = response.body()?.Token ?: ""
            runBlocking {
                tokenDataStore.saveToken(token)
            }
            Log.i("logs","Login Succesful, auth token stored : "+token)
        }
        return response
    }

    @POST("register/")
    override suspend fun register(@Body request: UserRegisterApiRequest): Response<UserRegisterApiResponse.Success> {
        val response = loginApi.register(request)

        if (response.isSuccessful) {
            tokenDataStore.saveToken(response.body()!!.data.Token)
        }
        return response
    }
}

