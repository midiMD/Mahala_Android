package com.neighborly.neighborlyandroid.login.models

data class LoginRequest(
    val email: String,
    val password: String
)
data class LoginResponse(
    val data: LoginData
)

data class LoginData(
    val status: Int,
    val Token: String, // Note: Capitalizing property name to match JSON
    val user: User
)