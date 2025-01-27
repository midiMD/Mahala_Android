package com.neighborly.neighborlyandroid.domain.repository

import android.content.Context
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.AddItemDetails
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail


interface InventoryRepository {
    suspend fun getItemDetail(itemId: Long) : Resource<InventoryItemDetail>
    suspend fun getItems():Resource<List<InventoryItem>>
    suspend fun addItem(context: Context, item:AddItemDetails):Resource<Unit>
    suspend fun deleteItem(itemId:Long):Resource<Unit>
    suspend fun updateItem(context:Context,itemId:Long,item:AddItemDetails):Resource<Unit>

}