package com.neighborly.neighborlyandroid.domain.model

import com.google.gson.annotations.SerializedName


data class MarketItemDetail(
    val id:Long? = null,
    val description:String? = null
)
data class MarketItem(
    val id:Long,
    val title: String,
    val ownerName: String,
    val dayCharge: Double,
    val category: Category?,
    val thumbnailUrl:String?,
    val distance:Double
)

data class MarketQuery(
    val searchQuery:String? = null,
    val filterCategoryIds:Set<Int>? = emptySet()
)