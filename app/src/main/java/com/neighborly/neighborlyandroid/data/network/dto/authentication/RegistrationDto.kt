package com.neighborly.neighborlyandroid.data.network.dto.authentication

import com.google.gson.annotations.SerializedName
import com.neighborly.neighborlyandroid.data.network.dto.ResponseErrorDetails
import com.neighborly.neighborlyandroid.domain.model.House
import com.neighborly.neighborlyandroid.domain.model.RegistrationDetails


data class RegisterResponse(
    val token:String?
)

data class HouseDto(
    val street:String,
    @SerializedName("house_number")val number:String,
    val postcode:String,
    @SerializedName("apartment_number")
    val apartmentNumber:String = "",
)

data class RegisterRequest(
    @SerializedName("full_name")
    val fullName:String,
    val email:String,
    val password:String,
    val house:HouseDto,


    ){
    companion object {
        // member function to fill teh attributes from RegistrationDetails object automatically as they are named the same
        fun from(details: RegistrationDetails): RegisterRequest {
            return RegisterRequest(
                fullName = details.fullName,
                email = details.email,
                password = details.password,
                house = HouseDto(street = details.street, number =details.number, postcode = details.postcode, apartmentNumber = details.apartmentNumber)
            )
        }
    }
}