package com.neighborly.neighborlyandroid.market.data

import android.util.Log
import com.neighborly.neighborlyandroid.common.networking.MarketApi
import com.neighborly.neighborlyandroid.common.networking.MarketService
import com.neighborly.neighborlyandroid.market.models.MarketResponseState

import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MarketRepository(private val marketService: MarketApi) {
    suspend fun requestMarketItems(requestBody: MarketSearchRequest): MarketResponseState = withContext(Dispatchers.IO){
        //Network and local storage IO operations should be done in IO Context
        var responseState: MarketResponseState

        try {
            val response = marketService.requestMarketItems(requestBody)
            Log.d("logs","MarketRepository: Market items response: " +  response.body()?.toString())
            responseState = MarketResponseState(response.body(), response.isSuccessful,response.errorBody().toString())

        }catch (e:Exception){
            e.message?.let { Log.d("logs", "Error in MarketRepository, requestMarketItems: "+it) }
            responseState = MarketResponseState(emptyList(),false,e.toString())
        }


        responseState // no "return" because we are using withContext
    }
}