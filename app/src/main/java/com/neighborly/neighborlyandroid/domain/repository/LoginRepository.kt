package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.LoginResponseState
import com.neighborly.neighborlyandroid.common.ResetPasswordResponseState

interface LoginRepository {
    suspend fun login(email:String, password:String): LoginResponseState
    suspend fun resetPassword(email: String):ResetPasswordResponseState
}