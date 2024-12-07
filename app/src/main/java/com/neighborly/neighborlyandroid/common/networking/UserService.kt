package com.neighborly.neighborlyandroid.common.networking


import TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.common.models.User
import com.neighborly.neighborlyandroid.market.models.MarketItem
import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import com.neighborly.neighborlyandroid.market.models.MarketSearchResponse
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class UserService(private val tokenDataStore: TokenDataStore):UserApi{
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
