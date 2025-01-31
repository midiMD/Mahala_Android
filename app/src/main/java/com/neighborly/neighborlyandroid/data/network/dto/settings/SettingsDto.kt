package com.neighborly.neighborlyandroid.data.network.dto.settings

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class PasswordChangeRequest(
    @SerializedName("old_password")val oldPassword:String,
    @SerializedName("new_password") val newPassword:String
)
data class EmailChangeRequest(
    @SerializedName("old_email")val oldEmail:String,
    @SerializedName("new_email")val newEmail:String,
    val password:String
)