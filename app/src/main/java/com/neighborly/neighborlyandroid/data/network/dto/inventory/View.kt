package com.neighborly.neighborlyandroid.data.network.dto.inventory

import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.LocalDateTime

data class InventoryItemResponse(
    val id:Long,
    val title: String,
    @SerializedName("price_per_day") val dayCharge: Double,
    @SerializedName("image_url") val thumbnailUrl:String,
)
data class InventoryItemsResponse(
    val items:List<InventoryItemResponse>
)
data class InventoryItemDetailResponse(
    val description:String?,
    @SerializedName("date_added") val dateAdded: LocalDateTime?
)