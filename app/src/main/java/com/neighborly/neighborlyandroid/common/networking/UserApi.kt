package com.neighborly.neighborlyandroid.common.networking

import com.neighborly.neighborlyandroid.common.models.User
import com.neighborly.neighborlyandroid.market.models.MarketItem
import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import com.neighborly.neighborlyandroid.market.models.MarketSearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi{
    @GET("/user/")
    suspend fun getUserInfo(): Response<User>
}

