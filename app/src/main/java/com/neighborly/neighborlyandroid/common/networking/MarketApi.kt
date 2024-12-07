package com.neighborly.neighborlyandroid.common.networking

import com.neighborly.neighborlyandroid.market.models.MarketItem
import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import com.neighborly.neighborlyandroid.market.models.MarketSearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi{
    @GET("/market/")
    suspend fun requestMarketItems(
        @Query("searchQuery") request: MarketSearchRequest): Response<List<MarketItem>>
}

