package com.neighborly.neighborlyandroid.common.composition

import MockTokenDataStoreImpl
import TokenDataStore
import TokenDataStoreImpl
import android.content.Context
import com.neighborly.neighborlyandroid.login.data.LoginRepository
import com.neighborly.neighborlyandroid.main.MainRepository
import com.neighborly.neighborlyandroid.common.networking.AuthorizedApiService
import com.neighborly.neighborlyandroid.common.networking.AuthorizedApiServiceImpl
import com.neighborly.neighborlyandroid.common.networking.UnAuthApiService
import com.neighborly.neighborlyandroid.common.networking.UnAuthApiServiceImpl
import com.neighborly.neighborlyandroid.registration.data.RegisterRepository

class AppCompositionRoot(applicationContext:Context) {

    public val tokenStore: TokenDataStore =MockTokenDataStoreImpl(context = applicationContext)
    private val unauthorizedApiService: UnAuthApiService = UnAuthApiServiceImpl(tokenDataStore = tokenStore)
    private val authorizedApiService: AuthorizedApiService = AuthorizedApiServiceImpl(tokenDataStore = tokenStore)

    public val mainRepository:MainRepository = MainRepository(tokenDataStore = tokenStore, authorizedApiService = authorizedApiService)
    public val loginRepository:LoginRepository get() = LoginRepository(apiService = unauthorizedApiService)
    public val registerRepository: RegisterRepository get() = RegisterRepository(apiService = unauthorizedApiService)


}