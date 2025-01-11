package com.neighborly.neighborlyandroid.data.network.retrofit

import com.neighborly.neighborlyandroid.domain.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserApi{
    @GET("/user/")
    suspend fun getUserInfo(): Response<User>
}

