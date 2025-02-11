package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.ResetPasswordResponseState
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.UserStatus

interface LoginRepository {
    suspend fun login(email:String, password:String): Resource<UserStatus>
    suspend fun resetPassword(email: String):ResetPasswordResponseState
    suspend fun validateAuthtoken():Resource<UserStatus> //
}