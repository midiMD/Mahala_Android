package com.neighborly.neighborlyandroid.login.models

import com.neighborly.neighborlyandroid.common.models.User

data class LoginRequest(
    val email: String,
    val password: String
)
data class LoginResponse(
    val status: Int,
    val Token: String, // Note: Capitalizing property name to match JSON
    val user: User
)
