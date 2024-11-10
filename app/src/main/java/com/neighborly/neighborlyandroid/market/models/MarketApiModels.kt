package com.neighborly.neighborlyandroid.market.models

import com.neighborly.neighborlyandroid.common.models.Category

data class MarketSearchRequest(
    val searchString: String,
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
    val ownerName: String,
    val dayCharge: Double,
    val category: Category,
    val thumbnailUrl:String,
    val distance:Double
)