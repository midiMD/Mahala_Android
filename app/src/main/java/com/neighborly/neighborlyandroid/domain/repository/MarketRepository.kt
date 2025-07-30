package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.domain.model.MarketQuery


interface MarketRepository {
    suspend fun searchMarketItems(searchQuery: MarketQuery): Resource<List<MarketItem>>

    suspend fun getItemDetail(itemId: Long) : Resource<MarketItemDetail>

}