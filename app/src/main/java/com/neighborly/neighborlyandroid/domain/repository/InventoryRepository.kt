package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail


interface InventoryRepository {
    suspend fun getItemDetail(itemId: Long) : Resource<InventoryItemDetail>
    suspend fun getItems():Resource<List<InventoryItem>>
}