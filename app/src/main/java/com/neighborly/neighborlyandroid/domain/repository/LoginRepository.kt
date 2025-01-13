package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.LoginResponseState

interface LoginRepository {
    suspend fun login(email:String, password:String): LoginResponseState
}