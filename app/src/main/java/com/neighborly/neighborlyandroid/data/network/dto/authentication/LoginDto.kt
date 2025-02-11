package com.neighborly.neighborlyandroid.data.network.dto.authentication

import com.google.gson.annotations.SerializedName
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemResponse
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.User
import com.neighborly.neighborlyandroid.domain.model.UserStatus

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse (
   @SerializedName("Token") val token: String,
   @SerializedName("is_address_verified") val isAddressVerified:Boolean
)
fun LoginResponse.toUserStatus(): UserStatus =
    UserStatus(
        isAddressVerified=isAddressVerified
    )

data class PasswordResetRequest(
    val email:String
)
data class ValidateAuthTokenResponse(
    @SerializedName("is_address_verified") val isAddressVerified: Boolean
)

fun ValidateAuthTokenResponse.toUserStatus():UserStatus =
    UserStatus(
        isAddressVerified = isAddressVerified
    )

fun MarketItemResponse.toMarketItem(): MarketItem =
    MarketItem(
        id = id,
        title = title,
        ownerName = ownerName,
        dayCharge = dayCharge,
        category = category,
        thumbnailUrl = thumbnailUrl,
        distance = distance,
    )

