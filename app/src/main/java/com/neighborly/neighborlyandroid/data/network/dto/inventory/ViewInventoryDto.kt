package com.neighborly.neighborlyandroid.data.network.dto.inventory

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class InventoryItemResponse(
    val id:Long,
    val title: String,
    @SerializedName("price_per_day") val dayCharge: Double,
    @SerializedName("thumbnail_url") val thumbnailUrl:String,
)
fun InventoryItemResponse.toInventoryItem(): InventoryItem =
    InventoryItem(
        id = id,
        title = title,
        dayCharge = dayCharge,
        category = null,
        thumbnailUrl = thumbnailUrl
    )
data class InventoryItemDetailResponse(
    val description:String?,
    @SerializedName("date_added") val dateAdded: String?
)

fun InventoryItemDetailResponse.toInventoryItemDetail():InventoryItemDetail =
    InventoryItemDetail(
        description=description,
        dateAdded = ZonedDateTime.parse(dateAdded)
    )