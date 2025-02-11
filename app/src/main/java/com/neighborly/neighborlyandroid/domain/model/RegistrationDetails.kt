package com.neighborly.neighborlyandroid.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class RegistrationDetails(
    var fullName:String,
    var email:String,
    var password:String,
    var street:String,
    var number:String,
    var postcode:String,
    var apartmentNumber:String,
    var isCheckedTAndC:Boolean
)
@Parcelize
data class LoggedInUserDetails(
    val isAddressVerified: Boolean
): Parcelable

