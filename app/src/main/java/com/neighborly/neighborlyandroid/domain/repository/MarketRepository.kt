package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.MarketItemResponseState
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemDetailResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketItemResponse
import com.neighborly.neighborlyandroid.data.network.dto.market.MarketSearchRequest
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.domain.model.MarketQuery

import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface MarketRepository {
    suspend fun searchMarketItems(searchQuery: MarketQuery): Resource<List<MarketItem>>

    suspend fun getItemDetail(itemId: Long) : Resource<MarketItemDetail>

}