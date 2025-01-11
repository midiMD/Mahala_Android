package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import android.util.Log
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



    override suspend fun requestInventoryItems(): Response<List<InventoryItemResponse>> {
        val inventoryApi = getAuthApi()
        Log.d("logs","InventoryService request the items in inventory: *****************")
        val response = inventoryApi.requestInventoryItems()
        Log.i("logs", "MarketService.requestMarketItems : $response")
        return response
    }
}
