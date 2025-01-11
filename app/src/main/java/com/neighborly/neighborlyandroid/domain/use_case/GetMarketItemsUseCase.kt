package com.neighborly.neighborlyandroid.domain.use_case

import android.util.Log
import coil3.network.HttpException
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketSearchRequest
import com.neighborly.neighborlyandroid.data.network.dto.market.toMarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

//class GetMarketItemsUseCase(
//    private val repository: MarketRepository
//){
//
//    operator fun invoke(searchQuery: MarketSearchRequest): Flow<Resource<List<MarketItem>>> = flow{
//        try{
//            emit(Resource.Loading())
//            val response = repository.requestMarketItems(searchQuery)
//            if (response.isSuccessful){
//                emit(Resource.Success(response.body()!!.map { item-> item.toMarketItem() }))
//            }else if (response.code() == 500){
//                //internal server error
//                emit(Resource.ServerError())
//            } else if (response.code() == 401 || response.code() == 403){ // unauthorized or forbidden
//                emit(Resource.AccessDenied())
//            }else{
//                emit(Resource.ServerError())
//            }
//
//            Log.d("logs","GetMarketUseCase: Market items response: ${response.body()}" )
//        }catch (e:HttpException){
//            emit(Resource.ClientError())
//        }catch (e:IOException){
//            emit(Resource.ClientError())
//        }
//
//    }
//}