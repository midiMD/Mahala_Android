package com.neighborly.neighborlyandroid.data.repository

import android.util.Log
import coil3.network.HttpException
import com.google.gson.Gson
import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import com.neighborly.neighborlyandroid.common.ResetPasswordResponseState
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.dto.ApiErrorResponse
import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.PasswordResetRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.toUserStatus
import com.neighborly.neighborlyandroid.data.network.retrofit.LoginService
import com.neighborly.neighborlyandroid.domain.model.User
import com.neighborly.neighborlyandroid.domain.model.UserStatus
import com.neighborly.neighborlyandroid.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okio.IOException


class LoginRepositoryImpl(private val apiService: LoginService,
                      private val tokenDataStore: TokenDataStore): LoginRepository {
    override suspend fun login(email: String, password: String): Resource<UserStatus> =
        withContext(Dispatchers.IO) {
            //Network and local storage IO operations should be done in IO Context

            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {

                    val successResponse = response.body()
                    val token: String = successResponse?.token ?: ""
                    runBlocking {
                        tokenDataStore.saveToken(token)

                    }
                    Log.i("logs", "Login Succesful, auth token stored : " + token)
                    Resource.Success(data = successResponse!!.toUserStatus())
                } else {
                    val apiError = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ApiErrorResponse::class.java
                    )
                    if (apiError.type == "client_error" && apiError.errors[0].code == "not_authenticated") {
                        Resource.Error.AccessDenied()
                    } else {
                        Resource.Error.ServerError()
                    }
                }
            } catch (e: Exception) {
                e.message?.let { Log.d("logs", "LoginRepository Error : " + it) }
                Resource.Error.ServerError()
            }


        }

    override suspend fun resetPassword(email: String): ResetPasswordResponseState =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.passwordReset(PasswordResetRequest(email))
                if (response.isSuccessful) {

                    ResetPasswordResponseState.Success
                } else {
                    val apiError = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ApiErrorResponse::class.java
                    )
                    Log.d("logs", "LoginRepository.resetPassword error : $apiError")
                    if (apiError.type == "client_error") {
                        ResetPasswordResponseState.Error.ClientError
                    } else {
                        ResetPasswordResponseState.Error.ServerError
                    }
                }
            } catch (e: Exception) {
                e.message?.let { Log.d("logs", "LoginRepository resetPassword Error : $it") }
                ResetPasswordResponseState.Error.ServerError
            }
        }

    override suspend fun validateAuthtoken(): Resource<UserStatus> = withContext(Dispatchers.IO) {
        val token = tokenDataStore.getToken()

        if (token.isNullOrEmpty()) {
            Resource.Error.AccessDenied<UserStatus>()
        } else {
            try {
                val response =
                    apiService.validateAuthToken()
                if (response.isSuccessful) {
                    Log.i("logs", "LoginRepo Auth Token validated : $token")
                    Resource.Success(response.body()!!.toUserStatus())
                } else if (response.code() == 401 || response.code() == 403) { // unauthorized or forbidden
                    Resource.Error.AccessDenied<UserStatus>()
                } else {
                    Resource.Error.ServerError<UserStatus>()
                }
            } catch (e: HttpException) {
                Resource.Error.ClientError<UserStatus>()
            } catch (e: IOException) {
                Resource.Error.NetworkError<UserStatus>()
            } catch (e: Exception) {
                Log.e("logs", e.toString())
                Resource.Error.ServerError<UserStatus>()
            }
        }
    }
}



