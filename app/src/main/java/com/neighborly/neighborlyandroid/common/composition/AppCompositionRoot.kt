package com.neighborly.neighborlyandroid.common.composition

import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import com.neighborly.neighborlyandroid.data.datastore.TokenDataStoreImpl
import android.content.Context
import com.neighborly.neighborlyandroid.data.network.retrofit.ChatService
import com.neighborly.neighborlyandroid.data.network.retrofit.ChatServiceImpl
import com.neighborly.neighborlyandroid.data.network.retrofit.InventoryApi
import com.neighborly.neighborlyandroid.data.network.retrofit.InventoryService

import com.neighborly.neighborlyandroid.data.network.retrofit.LoginService
import com.neighborly.neighborlyandroid.data.network.retrofit.MarketService
import com.neighborly.neighborlyandroid.data.network.retrofit.MockChatService
import com.neighborly.neighborlyandroid.data.network.retrofit.SettingsApi
import com.neighborly.neighborlyandroid.data.network.retrofit.SettingsService

import com.neighborly.neighborlyandroid.data.network.retrofit.UserService
import com.neighborly.neighborlyandroid.data.repository.ChatRepositoryImpl
import com.neighborly.neighborlyandroid.data.repository.InventoryRepositoryImpl
import com.neighborly.neighborlyandroid.data.repository.LoginRepositoryImpl
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository
import com.neighborly.neighborlyandroid.domain.repository.LoginRepository
import com.neighborly.neighborlyandroid.domain.repository.MarketRepository
import com.neighborly.neighborlyandroid.data.repository.MarketRepositoryImpl
import com.neighborly.neighborlyandroid.data.repository.RegisterRepositoryImpl
import com.neighborly.neighborlyandroid.data.repository.SettingsRepositoryImpl
import com.neighborly.neighborlyandroid.domain.repository.ChatRepository
import com.neighborly.neighborlyandroid.domain.repository.SettingsRepository


class AppCompositionRoot(applicationContext:Context) {

//    private val tokenStore: TokenDataStore =MockTokenDataStoreImpl(context = applicationContext)
    private val tokenStore: TokenDataStore = TokenDataStoreImpl(context = applicationContext)
    private val loginService: LoginService = LoginService(tokenDataStore = tokenStore)
    private val userService = UserService(tokenDataStore = tokenStore)
    private val marketService = MarketService(tokenDataStore = tokenStore)
    private val inventoryService = InventoryService(tokenDataStore = tokenStore)

    public val loginRepository: LoginRepository
        get() = LoginRepositoryImpl(
        apiService = loginService,
        tokenDataStore = tokenStore
    )
    public val registerRepository: RegisterRepositoryImpl get() = RegisterRepositoryImpl(apiService = loginService)
    public val marketRepository: MarketRepository get() = MarketRepositoryImpl(marketService = marketService)

    // Inventory
    public val inventoryRepository: InventoryRepository get() = InventoryRepositoryImpl(inventoryService = inventoryService)
    //Chat
    public val chatService: ChatService = ChatServiceImpl()
    public val chatRepository: ChatRepository get() = ChatRepositoryImpl(chatService = chatService)

    // Settings
    public val settingsService: SettingsService = SettingsService(tokenDataStore = tokenStore)
    public val settingsRepository:SettingsRepository = SettingsRepositoryImpl(service = settingsService)
}