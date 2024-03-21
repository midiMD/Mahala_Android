package com.neighborly.neighborlyandroid

import com.neighborly.neighborlyandroid.models.LoginRequest
import com.neighborly.neighborlyandroid.models.LoginResponse
import com.neighborly.neighborlyandroid.models.mockResponse

class LoginRepository {
    private val url = "127.0.0.1:8000/login/"
    fun makeRequest(request: LoginRequest): LoginResponse {
        return mockResponse
    }
}