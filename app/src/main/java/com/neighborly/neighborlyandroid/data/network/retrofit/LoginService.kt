package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginApiResponse
import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.RegisterRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.RegisterResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// TODO:
//
class LoginService(
    //private val tokenDataStore: TokenDataStore
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
    override suspend fun login(@Body request: LoginRequest): Response<LoginApiResponse.Success> {
        Log.d("logs","login request with: " + request.toString())
        val response= loginApi.login(request)
        Log.d("logs","Login api response " + response.toString())
        return response
        // it deals with storage of token in TokenDataStore

    }

    @POST("register/")
    override suspend fun register(@Body request: RegisterRequest):Response<Unit> {
        val response = loginApi.register(request)

        return response
    }
}

