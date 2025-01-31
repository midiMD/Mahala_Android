package com.neighborly.neighborlyandroid.data.repository

import android.util.Log
import coil3.network.HttpException
import com.neighborly.neighborlyandroid.common.PasswordChangeResponseState
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.dto.inventory.toInventoryItem
import com.neighborly.neighborlyandroid.data.network.dto.settings.PasswordChangeRequest
import com.neighborly.neighborlyandroid.data.network.retrofit.SettingsService
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

class SettingsRepositoryImpl(val service:SettingsService):SettingsRepository {
    override suspend fun logout(): Resource<Unit> =
        withContext(Dispatchers.IO) {
            //Network and local storage IO operations should be done in IO Context
            try {
                val response =
                    service.logout()

                if (response.isSuccessful) {
                    Log.i("logs","SettingsRepo: Log out success")
                    Resource.Success()
                } else if (response.code() == 500) {
                    //internal server error
                    Resource.Error.ServerError()
                } else if (response.code() == 401 || response.code() == 403) { // unauthorized or forbidden
                    Resource.Error.AccessDenied()
                } else {
                    Resource.Error.ServerError()
                }
            } catch (e: HttpException) {
                Resource.Error.ClientError<Unit>()
            } catch (e: IOException) {
                Resource.Error.NetworkError()
            } catch (e:Exception) {
                Log.e("logs",e.toString())
                Resource.Error.ServerError()
            }
        }

    override suspend fun passwordChange(oldPassword: String, newPassword: String): PasswordChangeResponseState =
        withContext(Dispatchers.IO){
            try {
                val response =
                    service.passwordChange(PasswordChangeRequest(oldPassword,newPassword))


                if (response.isSuccessful) {
                    Log.i("logs","SettingsRepo: Password change success")
                    PasswordChangeResponseState.Success
                }else if(response.code() == 404){
                    // password incorrect
                    PasswordChangeResponseState.Error.IncorrectPassword
                }
                else if (response.code() == 401 || response.code() == 403) { // unauthorized or forbidden
                    PasswordChangeResponseState.Error.AccessDenied // token has been corrupted -> logout OR that the old password provided is incorrect-> Display error
                } else {
                    PasswordChangeResponseState.Error.ServerError
                }
            } catch (e: HttpException) {
                PasswordChangeResponseState.Error.ClientError
            } catch (e: IOException) {
                PasswordChangeResponseState.Error.NetworkError
            } catch (e:Exception) {
                Log.e("logs",e.toString())
                PasswordChangeResponseState.Error.ServerError
            }
        }

}