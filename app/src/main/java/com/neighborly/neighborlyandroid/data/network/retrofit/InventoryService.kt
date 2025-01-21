package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import android.util.Log
import com.neighborly.neighborlyandroid.data.network.dto.inventory.InventoryItemDetailResponse
import com.neighborly.neighborlyandroid.data.network.dto.inventory.InventoryItemResponse

import retrofit2.Response

class InventoryService(private val tokenDataStore: TokenDataStore): InventoryApi {
    // Must include the auth token in all the requests
    private suspend fun getAuthApi(): InventoryApi {
        val token = tokenDataStore.getToken() // Directly assign the result
        val retrofit = AuthRetrofitClient(token= token ).getClient()
        val api = retrofit.create(InventoryApi::class.java)
        return api
    }



    override suspend fun requestItems(): Response<List<InventoryItemResponse>> {
        val inventoryApi = getAuthApi()
        Log.d("logs","InventoryService request the items in inventory: *****************")
        val response = inventoryApi.requestItems()
        Log.i("logs", "InventoryService.requestItems : $response")
        return response
    }

    override suspend fun requestItemDetail(itemId: Long): Response<InventoryItemDetailResponse> {
        val inventoryApi = getAuthApi()
        val response = inventoryApi.requestItemDetail(itemId)
        return response
    }

}
