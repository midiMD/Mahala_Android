package com.neighborly.neighborlyandroid.data.network.dto.authentication

import com.neighborly.neighborlyandroid.data.network.dto.ResponseErrorDetails
import com.neighborly.neighborlyandroid.domain.model.User

data class LoginRequest(
    val email: String,
    val password: String
)

sealed class LoginApiResponse {
    data class Success(
        val Token: String,
        val user: User
    )
    data class Error(
        val type:String,
        val errors:List<ResponseErrorDetails>,
    )

}
data class PasswordResetRequest(
    val email:String
)

sealed class PasswordResetApiResponse {
    data object Success
    data class Error(
        val type:String,
        val errors:List<ResponseErrorDetails>,
    )

}
