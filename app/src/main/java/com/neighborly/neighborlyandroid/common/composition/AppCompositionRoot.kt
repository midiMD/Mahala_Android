package com.neighborly.neighborlyandroid.common.composition

import MockTokenDataStoreImpl
import TokenDataStore
import TokenDataStoreImpl
import android.content.Context
import com.neighborly.neighborlyandroid.login.data.LoginRepository
import com.neighborly.neighborlyandroid.main.MainRepository
import com.neighborly.neighborlyandroid.networking.ApiService
import com.neighborly.neighborlyandroid.networking.ApiServiceImpl
import com.neighborly.neighborlyandroid.networking.AuthorizedApiService
import com.neighborly.neighborlyandroid.networking.AuthorizedApiServiceImpl

class AppCompositionRoot(applicationContext:Context) {

    public val tokenStore: TokenDataStore =TokenDataStoreImpl(context = applicationContext)
    private val unauthorizedApiService: ApiService= ApiServiceImpl(tokenDataStore = tokenStore)
    private val authorizedApiService: AuthorizedApiService= AuthorizedApiServiceImpl(tokenDataStore = tokenStore)

    public val mainRepository:MainRepository = MainRepository(tokenDataStore = tokenStore, authorizedApiService = authorizedApiService)
    public val loginRepository:LoginRepository get() = LoginRepository(apiService = unauthorizedApiService)

}