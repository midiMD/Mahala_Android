package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.domain.model.User
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class UserService(private val tokenDataStore: TokenDataStore): UserApi {
    private var token : String?

    init {
        // get the token from the datastore
        runBlocking {
            token = tokenDataStore.getToken() // Directly assign the result
            Log.i("logs","Auth Token: "+token)
        }
    }
    private val retrofit = AuthRetrofitClient(token= token ).getClient()
    private val userApi = retrofit.create(UserApi::class.java)
    override suspend fun getUserInfo(): Response<User> {
        val response = userApi.getUserInfo()
        return response
    }
}
