package com.neighborly.neighborlyandroid.data.repository

import android.util.Log
import coil3.network.HttpException
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketSearchRequest
import com.neighborly.neighborlyandroid.data.network.dto.market.toMarketItem
import com.neighborly.neighborlyandroid.data.network.dto.market.toMarketItemDetail

import com.neighborly.neighborlyandroid.data.network.retrofit.MarketApi
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.domain.model.MarketQuery
import com.neighborly.neighborlyandroid.domain.repository.MarketRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException


class MarketRepositoryImpl(private val marketService: MarketApi): MarketRepository {
    init{
        Log.d("logs","MArket Repo instantiated")
    }
    override suspend fun searchMarketItems(searchQuery: MarketQuery): Resource<List<MarketItem>> =
        withContext(Dispatchers.IO) {
            //Network and local storage IO operations should be done in IO Context
            //var responseState: MarketResponseState
            try {
                val response =
                    marketService.requestMarketItems(MarketSearchRequest.fromMarketQuery(searchQuery))

                if (response.isSuccessful) {
                    val marketItems:List<MarketItem> = response.body()!!.map { item -> item.toMarketItem() }
                    Log.i("logs","Market request is success : $marketItems")
                    Resource.Success(marketItems)
                } else if (response.code() == 500) {
                    //internal server error
                    Resource.Error.ServerError()
                } else if (response.code() == 401 || response.code() == 403) { // unauthorized or forbidden
                    Resource.Error.AccessDenied()
                } else {
                    Resource.Error.ServerError()
                }
            } catch (e: HttpException) {
                Resource.Error.ClientError<List<MarketItem>>()
            } catch (e: IOException) {
                Resource.Error.NetworkError<List<MarketItem>>()
            } catch (e:Exception) {
                Log.e("logs",e.toString())
                Resource.Error.ServerError()
            }
        }

    override suspend fun getItemDetail(itemId: Long): Resource<MarketItemDetail> =
        withContext(Dispatchers.IO) {
            //Network and local storage IO operations should be done in IO Context

            try {
                val response = marketService.requestMarketItemDetail(itemId)

                if (response.isSuccessful) {
                    val itemDetail: MarketItemDetail = response.body()!!.toMarketItemDetail()

                    Resource.Success(data = itemDetail)
                } else if (response.code() == 500) {
                    //internal server error
                    Resource.Error.ServerError()
                } else if (response.code() == 401 || response.code() == 403) { // unauthorized or forbidden
                    Resource.Error.AccessDenied()
                } else {
                    Resource.Error.ServerError()
                }

            }catch (e: HttpException) {
                Log.d("logs","MarketRepository getItemDetail error : $e")
                Resource.Error.ClientError()

            } catch (e: IOException) {
                Log.d("logs","MarketRepository getItemDetail error : $e")
                Resource.Error.NetworkError()
            } catch(e:Exception){
                Log.d("logs","MarketRepository getItemDetail error : $e")
                Resource.Error.ServerError()
            }
        }
}

