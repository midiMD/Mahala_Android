package com.neighborly.neighborlyandroid.models

data class LoginResponse(
    val token: String,  // Assuming you receive a token
    val userId: Int,    // Or any other user data
    val success: Boolean // Sometimes APIs include a success indicator
)

val mockResponse: LoginResponse = LoginResponse("mocktoken123123", userId = 1, success = true)