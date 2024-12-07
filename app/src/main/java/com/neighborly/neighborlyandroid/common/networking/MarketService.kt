package com.neighborly.neighborlyandroid.common.networking


import TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.market.models.MarketItem
import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import com.neighborly.neighborlyandroid.market.models.MarketSearchResponse
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class MarketService(private val tokenDataStore: TokenDataStore):MarketApi{
    suspend fun getAuthMarketApi(): MarketApi{
        val token = tokenDataStore.getToken() // Directly assign the result
        val retrofit = AuthRetrofitClient(token= token ).getClient()
        val marketApi = retrofit.create(MarketApi::class.java)
        return marketApi
    }



    override suspend fun requestMarketItems(request:MarketSearchRequest): Response<List<MarketItem>> {
        val marketApi = getAuthMarketApi()
        Log.d("logs","MarketService.requestMarketItems: *****************")
        val response = marketApi.requestMarketItems(request)
        Log.i("logs", "MarketService.requestMarketItems : $response")
        return response
    }
}
