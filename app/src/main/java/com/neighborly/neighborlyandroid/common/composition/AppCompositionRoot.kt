package com.neighborly.neighborlyandroid.common.composition

import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import com.neighborly.neighborlyandroid.data.datastore.TokenDataStoreImpl
import android.content.Context
import com.neighborly.neighborlyandroid.data.network.retrofit.InventoryService

import com.neighborly.neighborlyandroid.data.network.retrofit.LoginService
import com.neighborly.neighborlyandroid.data.network.retrofit.MarketService

import com.neighborly.neighborlyandroid.data.network.retrofit.UserService
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository
import com.neighborly.neighborlyandroid.domain.repository.LoginRepository
import com.neighborly.neighborlyandroid.domain.repository.MarketRepository
import com.neighborly.neighborlyandroid.domain.repository.MarketRepositoryImpl
import com.neighborly.neighborlyandroid.domain.repository.RegisterRepository


class AppCompositionRoot(applicationContext:Context) {

//    private val tokenStore: TokenDataStore =MockTokenDataStoreImpl(context = applicationContext)
    private val tokenStore: TokenDataStore = TokenDataStoreImpl(context = applicationContext)
    private val loginService: LoginService = LoginService()
    private val userService = UserService(tokenDataStore = tokenStore)
    private val marketService = MarketService(tokenDataStore = tokenStore)
    private val inventoryService = InventoryService(tokenDataStore = tokenStore)

    public val loginRepository: LoginRepository
        get() = LoginRepository(
        apiService = loginService,
        tokenDataStore = tokenStore
    )
    public val registerRepository: RegisterRepository get() = RegisterRepository(apiService = loginService)
    public val marketRepository: MarketRepository get() = MarketRepositoryImpl(marketService = marketService)
    // Inventory
    public val inventoryRepository: InventoryRepository get() = InventoryRepository(inventoryService = inventoryService)
}