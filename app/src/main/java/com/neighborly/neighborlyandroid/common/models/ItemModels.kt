package com.neighborly.neighborlyandroid.common.models

enum class Category(val id: Int, val text: String) {
    ELECTRONICS(1, "Electronics"),
    FURNITURE(2, "Furniture"),
    CLOTHING(3, "Clothing"),
    BOOKS(4, "Books"),
    TOYS(5, "Toys"),
    SPORTS_AND_OUTDOORS(6, "Sports & Outdoors"),
    HOME_AND_GARDEN(7, "Home & Garden"),
    PETS(8, "Pets"),
    MUSIC_INSTRUMENTS(9, "Musical Instruments"),
    OFFICE_SUPPLIES(10, "Office Supplies"),
    BABY_PRODUCTS(11, "Baby Products"),
    BEAUTY_AND_PERSONAL_CARE(12, "Beauty & Personal Care"),
    HEALTH_AND_WELLNESS(13, "Health & Wellness"),
    ART_AND_COLLECTIBLES(14, "Art & Collectibles"),
    SERVICES(15, "Services"),
    TOOLS_AND_EQUIPMENT(16, "Tools & Equipment"),
    APPLIANCES(17, "Appliances");

    companion object {
        // Helper function to find a category by ID
        fun fromId(id: Int): Category? = entries.find { it.id == id }
    }
}
enum class SortBy(val id: Int,val text:String){
    NOTHING(0,""),
    PRICE(1,"Price"),
    DISTANCE(2,"Distance")
}

