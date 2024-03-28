package com.neighborly.neighborlyandroid

import android.app.Application
import kotlinx.coroutines.CoroutineScope


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

//        tokenRetrievalScope = CoroutineScope(Dispatchers.IO) // Create a custom scope
//        tokenRetrievalScope?.launch {
//            token = dataStore.getToken()
//            // Process the token
//            tokenRetrievalScope?.cancel()  // Cancel the scope after token retrieval
//        }
    }
}