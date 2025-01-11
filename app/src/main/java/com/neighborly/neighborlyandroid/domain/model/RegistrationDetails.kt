package com.neighborly.neighborlyandroid.domain.model


data class RegistrationDetails(
    var fullName:String,
    var email:String,
    var password:String,
    var street:String,
    var number:String,
    var postcode:String,
    var apartmentNumber:String,
    var isCheckedTAndC:Boolean
)

