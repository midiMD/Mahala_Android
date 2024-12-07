package com.neighborly.neighborlyandroid.main

import TokenDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import com.neighborly.neighborlyandroid.common.networking.UserService

class MainRepository(
  private val tokenDataStore: TokenDataStore,
    private val userService: UserService
) {

    suspend fun validateToken(): Boolean {
        return withContext(Dispatchers.IO) { // Switch to IO for dataStore access
            val token = tokenDataStore.getToken()
            Log.i("logs", "MainRepository: Token retrieved from datastor: " + token )
            if (token.isNullOrEmpty()) {
                return@withContext false
            } else {
                Log.i("logs","MainRespository: token is not empty")
                try {
                    val response = userService.getUserInfo()
                    return@withContext response.isSuccessful// Return true if the API call was successful
                     // Check response
                } catch (e: Exception) {
                    Log.d("myError",e.toString())
                    return@withContext false // Handle API call errors
                }
            }
        }
    }
}