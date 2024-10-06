package com.neighborly.neighborlyandroid.market.models

data class MarketSearchRequest(
    val searchString: String,
    val category: Int
){
    init {
        require(category >= 0 && category< ItemCategoriesList.size) {
            "Invalid category index. Must be between 0 and ${ItemCategoriesList.size - 1}."
        }
    }
}
data class MarketSearchResponse(
    val data: List<Item> = emptyList()
)
data class MarketItem(
    val distance: Int,
    val item: Item, // Note: Capitalizing property name to match JSON

)
// we will represent a category as an int `c` s.t. the name of the category is ItemCategoriesList[`c`]
val ItemCategoriesList: List<String> = listOf("DIY", "Entertainment", "Garden", "Cleaning")


data class Item(
    val id:Int,
    val name: String,
    val ownerName: String,
    val dayCharge: Double,
    val category: Int,
    val thumbnail
) {
    init {
        require(category >= 0 && category< ItemCategoriesList.size) {
            "Invalid category index. Must be between 0 and ${ItemCategoriesList.size - 1}."
        }
    }
}