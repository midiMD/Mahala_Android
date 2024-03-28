package com.neighborly.neighborlyandroid.main

import TokenDataStore
import com.neighborly.neighborlyandroid.networking.AuthorizedApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(
    private val tokenDataStore: TokenDataStore,
    private val authorizedApiService: AuthorizedApiService // Inject this
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
                    return@withContext false // Handle API call errors
                }
            }
        }
    }
}