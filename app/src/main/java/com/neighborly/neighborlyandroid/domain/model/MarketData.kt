package com.neighborly.neighborlyandroid.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketItemDetail(
    val id:Long? = null,
    val description:String? = null
) : Parcelable
@Parcelize
data class MarketItem(
    val id:Long,
    val title: String,
    val ownerName: String,
    val dayCharge: Double,
    val category: Category?,
    val thumbnailUrl:String?,
    val distance:Double
) : Parcelable

data class MarketQuery(
    val searchQuery:String? = null,
    val filterCategoryIds:Set<Int>? = emptySet()
)