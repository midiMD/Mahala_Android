package com.neighborly.neighborlyandroid.market.data

import android.util.Log
import com.neighborly.neighborlyandroid.common.networking.AuthorizedApiService
import com.neighborly.neighborlyandroid.market.models.MarketResponseState

import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MarketRepository(private val apiService: AuthorizedApiService) {
    suspend fun requestMarketItems(requestBody: MarketSearchRequest): MarketResponseState = withContext(Dispatchers.IO){
        //Network and local storage IO operations should be done in IO Context
        var responseState: MarketResponseState

        try {
            val response = apiService.requestMarketItems(requestBody)
            Log.d("logs","Market items response: " +  response.body()?.data.toString())
            responseState = MarketResponseState(response.body()?.data, response.isSuccessful,response.errorBody().toString())

        }catch (e:Exception){
            e.message?.let { Log.d("logs", "Error in MarketRepository, requestMarketItems: "+it) }
            responseState = MarketResponseState(emptyList(),false,e.toString())
        }


        responseState // no "return" because we are using withContext
    }
}