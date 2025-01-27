package com.neighborly.neighborlyandroid.data.network.retrofit


import com.neighborly.neighborlyandroid.data.network.dto.inventory.InventoryItemDetailResponse
import com.neighborly.neighborlyandroid.data.network.dto.inventory.InventoryItemResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemDetailResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface InventoryApi{
    @GET("/inventory/items")
    suspend fun requestItems(
    ): Response<List<InventoryItemResponse>>
    @GET("/inventory/items/detail")
    suspend fun requestItemDetail(
        @Query("id") itemId: Long
    ): Response<InventoryItemDetailResponse>
    @DELETE("inventory/items/delete")
    suspend fun deleteItem(
        @Query("id") itemId:Long
    ):Response<Unit>
    @POST("/inventory/upload")
    @Multipart
    suspend fun uploadItem(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price_per_day") price: RequestBody,
        @Part("categories") categories: RequestBody,
    ):Response<Unit>

}

