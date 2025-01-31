package com.neighborly.neighborlyandroid.data.network.retrofit

import android.util.Log
import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import com.neighborly.neighborlyandroid.data.network.dto.settings.PasswordChangeRequest
import retrofit2.Response

class SettingsService(private val tokenDataStore: TokenDataStore):SettingsApi {
    private suspend fun getAuthApi(): SettingsApi {
        val token = tokenDataStore.getToken()
        val retrofit = AuthRetrofitClient(token= token ).getClient()
        val api = retrofit.create(SettingsApi::class.java)
        return api
    }
    override suspend fun logout(): Response<Unit> {
        val api = getAuthApi()
        val response = api.logout()
        Log.i("logs","SettingsService.logout")
        return response
    }

    override suspend fun passwordChange(request: PasswordChangeRequest): Response<Unit> {
        val api = getAuthApi()
        val response = api.passwordChange(request)
        Log.i("logs","SettingsService.passwordChange($request)")
        return response
    }

}