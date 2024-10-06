package com.neighborly.neighborlyandroid.market.data

import android.util.Log
import com.neighborly.neighborlyandroid.common.networking.AuthorizedApiService

import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MarketRepository(private val apiService: AuthorizedApiService) {
    suspend fun requestMarketItems(requestBody: MarketSearchRequest): Boolean = withContext(Dispatchers.IO){
        //Network and local storage IO operations should be done in IO Context
        var isAuth: Boolean = false
        try {
            val response = apiService.login(requestBody)
            when (response.code()){
                200 -> isAuth = true
                404 -> isAuth=false
            }

            response.body()?.data?.let { Log.d("token", it.Token) }

        }catch (e:Exception){
            e.message?.let { Log.d("LoginViewModelError", it) }
        }


        isAuth // no "return" because we are using withContext
    }
}