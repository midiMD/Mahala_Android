package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemDetailResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketSearchRequest

import retrofit2.Response

class MarketService(private val tokenDataStore: TokenDataStore): MarketApi {
    // Requests are authenticated with the auth token from datastore
    private suspend fun getAuthMarketApi(): MarketApi {
        val token = tokenDataStore.getToken()
        val retrofit = AuthRetrofitClient(token= token ).getClient()
        val marketApi = retrofit.create(MarketApi::class.java)
        return marketApi
    }



    override suspend fun requestMarketItems(request: MarketSearchRequest): Response<List<MarketItemResponse>> {
        val marketApi = getAuthMarketApi()
        val response = marketApi.requestMarketItems(request)
        Log.i("logs", "MarketService.requestMarketItems : ${response.body()}")
        return response
    }

    override suspend fun requestMarketItemDetail(itemId: Long): Response<MarketItemDetailResponse> {
        val marketApi = getAuthMarketApi()
        val response = marketApi.requestMarketItemDetail(itemId)
        Log.i("logs","MarketService.requestMarketItemDetail(itemId = $itemId) response : $response")
        return response
    }
}
