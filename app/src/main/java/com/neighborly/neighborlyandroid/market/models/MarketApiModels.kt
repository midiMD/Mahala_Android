package com.neighborly.neighborlyandroid.market.models

import com.google.gson.annotations.SerializedName
import com.neighborly.neighborlyandroid.common.models.Category

data class MarketSearchRequest(
    val searchQuery: String,
    val categoriesList: List<Int>  // Id of the categories
){

}
data class MarketSearchResponse(
    val data: List<MarketItem> = emptyList()
)

data class MarketResponseState(
    val data:List<MarketItem>? = emptyList(),
    var isSuccess:Boolean = false,
    val error: String? = null
)
// we will represent a category as an int `c` s.t. the name of the category is ItemCategoriesList[`c`]

// for an item displayed in market view
data class MarketItem(
    val id:Long,
    val title: String,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("price_per_day") val dayCharge: Double,
    val category: Category,
    @SerializedName("image_url") val thumbnailUrl:String,
    val distance:Double
)