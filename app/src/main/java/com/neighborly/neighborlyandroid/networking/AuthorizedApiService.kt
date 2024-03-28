package com.neighborly.neighborlyandroid.networking


import com.neighborly.neighborlyandroid.login.models.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

class AuthInterceptor(private val token: String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response{
        // if token="" that means there is no token in the dataStore
        return if (token ==""){
            chain.proceed(chain.request().newBuilder().build())

        }else{
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Token $token")
                .build()
            chain.proceed(request)
        }

    }
}
class AuthorizedApiServiceImpl(private val token: String) : AuthorizedApiService {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/"
    }

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val authOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(token))
        .addInterceptor(loggingInterceptor)
        .build()
    private val authorizedRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(authOkHttpClient)
        .build()
    private val authorizedApiService = authorizedRetrofit.create(AuthorizedApiService::class.java)

    @GET("/main/")
    override suspend fun getUserInfo(): Response<User> {
        return authorizedApiService.getUserInfo()
    }
}
interface AuthorizedApiService{

    @GET("/main/")
    suspend fun getUserInfo(): Response<User>
}