package com.neighborly.neighborlyandroid.common.composition

import MockTokenDataStoreImpl
import TokenDataStore
import TokenDataStoreImpl
import android.content.Context
import com.neighborly.neighborlyandroid.login.data.LoginRepository
import com.neighborly.neighborlyandroid.main.MainRepository
import com.neighborly.neighborlyandroid.common.networking.LoginService
import com.neighborly.neighborlyandroid.common.networking.MarketService

import com.neighborly.neighborlyandroid.common.networking.UserService
import com.neighborly.neighborlyandroid.market.data.MarketRepository
import com.neighborly.neighborlyandroid.registration.data.RegisterRepository

class AppCompositionRoot(applicationContext:Context) {

//    private val tokenStore: TokenDataStore =MockTokenDataStoreImpl(context = applicationContext)
    private val tokenStore: TokenDataStore =TokenDataStoreImpl(context = applicationContext)
    private val loginService:LoginService = LoginService(tokenDataStore = tokenStore)
    private val userService = UserService(tokenDataStore = tokenStore)
    private val marketService = MarketService(tokenDataStore = tokenStore)
//    private val unauthorizedApiService: UnAuthApiService = UnAuthApiServiceImpl(tokenDataStore = tokenStore)
//    private val authorizedApiService: AuthorizedApiService = AuthorizedApiServiceImpl(tokenDataStore = tokenStore)

    public val mainRepository:MainRepository = MainRepository(tokenDataStore = tokenStore, userService = userService)
    public val loginRepository:LoginRepository get() = LoginRepository(apiService = loginService)
    public val registerRepository: RegisterRepository get() = RegisterRepository(apiService = loginService)
    public val marketRepository: MarketRepository get() = MarketRepository(marketService = marketService)

}