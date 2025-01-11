package com.neighborly.neighborlyandroid.data.network.retrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
open class RetrofitClient(){
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/"
    }
    open var okHttpClientBuilder= OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)

    private val retrofitClientBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun getClient():Retrofit {

        val okHttpClient  = okHttpClientBuilder.build()
        val retrofitClient = retrofitClientBuilder.client(okHttpClient).build()
        return retrofitClient

    }
}
class AuthInterceptor(private val token: String?) : Interceptor {
    // Appends the auth token to a request if the token exists in DataStore

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
class AuthRetrofitClient(private val token:String?): RetrofitClient() {
    init {
        okHttpClientBuilder = okHttpClientBuilder.addInterceptor(AuthInterceptor(token))
    }

}