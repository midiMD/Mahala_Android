package com.neighborly.neighborlyandroid.data.repository

import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.retrofit.InventoryService
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository

class InventoryRepositoryImpl(inventoryService: InventoryService):InventoryRepository {
    override suspend fun getItemDetail(itemId: Long): Resource<InventoryItemDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun getInventoryItems(): Resource<List<InventoryItem>> {
        TODO("Not yet implemented")
    }
}