package com.neighborly.neighborlyandroid.domain.model

// Successful Login


data class User(
    val full_name: String,
    val email: String,
    val house: House
)

data class House(
    val postcode: String,
    val house_number: String,
    val street: String,
    val apartment_number: String = ""
)




