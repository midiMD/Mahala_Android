package com.neighborly.neighborlyandroid.data.network.dto.authentication

import com.google.gson.annotations.SerializedName
import com.neighborly.neighborlyandroid.data.network.dto.ResponseErrorDetails
import com.neighborly.neighborlyandroid.domain.model.RegistrationDetails


data class RegisterResponse(
    val token:String?
)


data class RegisterRequest(
    @SerializedName("full_name")
    var fullName:String,
    var email:String,
    var password:String,
    var street:String,
    var number:String,
    var postcode:String,
    @SerializedName("apartment_number")
    var apartmentNumber:String,

    ){
    companion object {
        // member function to fill teh attributes from RegistrationDetails object automatically as they are named the same
        fun from(details: RegistrationDetails): RegisterRequest {
            return RegisterRequest(
                fullName = details.fullName,
                email = details.email,
                password = details.password,
                street = details.street,
                number = details.number,
                postcode = details.postcode,
                apartmentNumber = details.apartmentNumber
            )
        }
    }
}