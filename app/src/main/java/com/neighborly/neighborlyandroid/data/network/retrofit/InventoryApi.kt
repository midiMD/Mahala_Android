package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.network.dto.inventory.InventoryItemDetailResponse
import com.neighborly.neighborlyandroid.data.network.dto.inventory.InventoryItemResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface InventoryApi{
    @GET("/inventory/items")
    suspend fun requestItems(
    ): Response<List<InventoryItemResponse>>
    @GET("/inventory/items/detail")
    suspend fun requestItemDetail(
        @Query("id") itemId: Long
    ): Response<InventoryItemDetailResponse>

}

