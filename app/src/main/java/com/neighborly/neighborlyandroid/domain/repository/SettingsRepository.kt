package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.PasswordChangeResponseState
import com.neighborly.neighborlyandroid.common.Resource

interface SettingsRepository {
    suspend fun logout(): Resource<Unit>
    suspend fun passwordChange(oldPassword:String,newPassword:String):PasswordChangeResponseState

}