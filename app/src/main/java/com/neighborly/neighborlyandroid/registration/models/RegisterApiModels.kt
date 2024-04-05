package com.neighborly.neighborlyandroid.registration.models

import com.google.gson.annotations.SerializedName
import com.neighborly.neighborlyandroid.common.models.ErrorDetails
import com.neighborly.neighborlyandroid.common.models.House
import com.neighborly.neighborlyandroid.common.models.User

data class UserRegisterApiRequest(
    val email: String,
    val password: String,
    @SerializedName("full_name")
    val fullName:String
)


sealed class UserRegisterApiResponse {

    data class Success(
        val data: UserRegisterApiResponseData
    )

    data class Error(
        val errors:List<ErrorDetails>,
        )

    data class ErrorDetails(
        val detail:String,
        val status:String,
        val code:String,

    )
    data class UserRegisterApiResponseData(
        val status: Int,
        val Token:String,
        val user:User
    )

}



