package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemDetailResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketSearchRequest

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi{
    @GET("/market/")
    suspend fun requestMarketItems(
        @Query("search_query") request: MarketSearchRequest
    ): Response<List<MarketItemResponse>>
    @GET("/market/item")
    suspend fun requestMarketItemDetail(
        @Query("id") itemId: Long
    ): Response<MarketItemDetailResponse>
}

