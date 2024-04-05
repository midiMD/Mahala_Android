package com.neighborly.neighborlyandroid.main

import TokenDataStore
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.common.networking.AuthorizedApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

class MainRepository(
  private val tokenDataStore: TokenDataStore,
    private val authorizedApiService: AuthorizedApiService
) {

    suspend fun validateToken(): Boolean {
        return withContext(Dispatchers.IO) { // Switch to IO for dataStore access
            val token = tokenDataStore.getToken()
            if (token.isEmpty()) {
                return@withContext false
            } else {
                try {
                    authorizedApiService.getUserInfo().isSuccessful // Check response
                    return@withContext true// Return true if the API call was successful
                } catch (e: Exception) {
                    Log.d("myError",e.toString())
                    return@withContext false // Handle API call errors
                }
            }
        }
    }
}