package com.neighborly.neighborlyandroid.common.models

enum class Category(val id: Int, val text: String) {
    ELECTRONICS(1, "Electronics"),
    FURNITURE(2, "Furniture"),
    CLOTHING(3, "Clothing"),
    BOOKS(4, "Books"),
    TOYS(5, "Toys"),
    SPORTS_AND_OUTDOORS(6, "Sports & Outdoors"),
    HOME_AND_GARDEN(7, "Home & Garden"),
    VEHICLES(8, "Vehicles"),
    REAL_ESTATE(9, "Real Estate"),
    PETS(10, "Pets"),
    MUSIC_INSTRUMENTS(11, "Musical Instruments"),
    OFFICE_SUPPLIES(12, "Office Supplies"),
    BABY_PRODUCTS(13, "Baby Products"),
    BEAUTY_AND_PERSONAL_CARE(14, "Beauty & Personal Care"),
    HEALTH_AND_WELLNESS(15, "Health & Wellness"),
    ART_AND_COLLECTIBLES(16, "Art & Collectibles"),
    SERVICES(17, "Services"),
    TICKETS(18, "Tickets"),
    TOOLS_AND_EQUIPMENT(19, "Tools & Equipment"),
    APPLIANCES(20, "Appliances");

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

