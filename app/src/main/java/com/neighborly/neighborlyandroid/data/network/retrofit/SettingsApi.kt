package com.neighborly.neighborlyandroid.data.network.retrofit

import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemDetailResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketSearchRequest
import com.neighborly.neighborlyandroid.data.network.dto.settings.PasswordChangeRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Query
import retrofit2.http.PUT

interface SettingsApi {
    @HEAD("/logout/")
    suspend fun logout(
    ): Response<Unit>
    @PUT("/settings/password-change/")
    suspend fun passwordChange(
        @Query("values") request: PasswordChangeRequest
    ): Response<Unit>
}