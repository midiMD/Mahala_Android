package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.LoginResponseState
import com.neighborly.neighborlyandroid.common.ResetPasswordResponseState
import com.neighborly.neighborlyandroid.common.Resource

interface LoginRepository {
    suspend fun login(email:String, password:String): LoginResponseState
    suspend fun resetPassword(email: String):ResetPasswordResponseState
    suspend fun validateAuthtoken():Resource<Unit>
}