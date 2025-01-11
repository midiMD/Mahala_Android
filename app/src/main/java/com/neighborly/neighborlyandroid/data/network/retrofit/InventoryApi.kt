package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.network.dto.inventory.InventoryItemResponse
import retrofit2.Response

interface InventoryApi{
    suspend fun requestInventoryItems(
    ): Response<List<InventoryItemResponse>>
}

