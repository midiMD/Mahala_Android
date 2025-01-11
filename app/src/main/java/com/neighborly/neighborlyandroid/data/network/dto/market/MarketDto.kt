package com.neighborly.neighborlyandroid.data.network.dto.market

import com.google.gson.annotations.SerializedName
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.domain.model.MarketQuery

data class MarketSearchRequest(
    @SerializedName("search_query") val searchQuery: String?,
    @SerializedName("category")  val filterCategoryIds:Set<Int>? = emptySet()
){companion object {
    // member function to fill teh attributes from RegistrationDetails object automatically as they are named the same
    fun fromMarketQuery(marketQuery: MarketQuery): MarketSearchRequest {
        return MarketSearchRequest(
            searchQuery = marketQuery.searchQuery,
            filterCategoryIds = marketQuery.filterCategoryIds
        )
    }
}}

//data class MarketSearchResponse(
//    val data: List<MarketItemResponse> = emptyList()
//)
//
//data class MarketResponseState(
//    val data:List<MarketItemResponse>? = emptyList(),
//    var isSuccess:Boolean = false,
//    val error: String? = null
//)
// we will represent a category as an int `c` s.t. the name of the category is ItemCategoriesList[`c`]

// for an item displayed in market view
data class MarketItemResponse(
    val id:Long,
    val title: String,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("price_per_day") val dayCharge: Double,
    val category: Category?,
    @SerializedName("image_url") val thumbnailUrl:String?,
    val distance:Double
)
fun MarketItemResponse.toMarketItem(): MarketItem =
    MarketItem(
        id = id,
        title = title,
        ownerName = ownerName,
        dayCharge = dayCharge,
        category = category,
        thumbnailUrl = thumbnailUrl,
        distance = distance,
    )

data class MarketItemDetailResponse(
    @SerializedName("item_id") val id:Long,
    val description:String
)
fun MarketItemDetailResponse.toMarketItemDetail():MarketItemDetail =
    MarketItemDetail(
        id = id,
        description = description,
    )


