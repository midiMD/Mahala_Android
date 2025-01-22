package com.neighborly.neighborlyandroid.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import coil3.network.HttpException
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.dto.inventory.toInventoryItem
import com.neighborly.neighborlyandroid.data.network.dto.inventory.toInventoryItemDetail
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketSearchRequest
import com.neighborly.neighborlyandroid.data.network.dto.market.toMarketItem
import com.neighborly.neighborlyandroid.data.network.dto.market.toMarketItemDetail
import com.neighborly.neighborlyandroid.data.network.retrofit.InventoryService
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.domain.model.MarketQuery
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
class InventoryRepositoryImpl(private val inventoryService: InventoryService):InventoryRepository {
    init {
        Log.d("logs","Inventory Repo instantiated")
    }
    override suspend fun getItemDetail(itemId: Long): Resource<InventoryItemDetail> {
        return withContext(Dispatchers.IO) {
            //Network and local storage IO operations should be done in IO Context

            try {
                val response = inventoryService.requestItemDetail(itemId)

                if (response.isSuccessful) {
                    val itemDetail: InventoryItemDetail = response.body()!!.toInventoryItemDetail()

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
                Log.d("logs","InventoryRepository getItemDetail error : $e")
                Resource.Error.ClientError()

            } catch (e: IOException) {
                Log.d("logs","Inventory Repository getItemDetail error : $e")
                Resource.Error.NetworkError()
            } catch(e:Exception){
                Log.d("logs","Inventory Repository getItemDetail error : $e")
                Resource.Error.ServerError()
            }
        }
    }

    override suspend fun getItems(): Resource<List<InventoryItem>> =
        withContext(Dispatchers.IO) {
            //Network and local storage IO operations should be done in IO Context
            //var responseState: MarketResponseState
            try {
                val response =
                    inventoryService.requestItems()

                if (response.isSuccessful) {
                    val items:List<InventoryItem> = response.body()!!.map { item -> item.toInventoryItem() }
                    Log.i("logs","Inventory items request success : $items")
                    Resource.Success(items)
                } else if (response.code() == 500) {
                    //internal server error
                    Resource.Error.ServerError()
                } else if (response.code() == 401 || response.code() == 403) { // unauthorized or forbidden
                    Resource.Error.AccessDenied()
                } else {
                    Resource.Error.ServerError()
                }
            } catch (e: HttpException) {
                Resource.Error.ClientError<List<InventoryItem>>()
            } catch (e: IOException) {
                Resource.Error.NetworkError<List<InventoryItem>>()
            } catch (e:Exception) {
                Log.e("logs",e.toString())
                Resource.Error.ServerError()
            }
        }
}