package com.neighborly.neighborlyandroid.data.repository

import android.util.Log
import coil3.network.HttpException
import com.google.gson.Gson
import com.neighborly.neighborlyandroid.data.datastore.TokenDataStore
import com.neighborly.neighborlyandroid.common.LoginResponseState
import com.neighborly.neighborlyandroid.common.ResetPasswordResponseState
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.data.network.dto.ApiErrorResponse
import com.neighborly.neighborlyandroid.data.network.dto.authentication.LoginRequest
import com.neighborly.neighborlyandroid.data.network.dto.authentication.PasswordResetRequest
import com.neighborly.neighborlyandroid.data.network.retrofit.LoginService
import com.neighborly.neighborlyandroid.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okio.IOException


class LoginRepositoryImpl(private val apiService: LoginService,
                      private val tokenDataStore: TokenDataStore): LoginRepository {
    override suspend fun login(email: String, password: String): LoginResponseState =
        withContext(Dispatchers.IO) {
            //Network and local storage IO operations should be done in IO Context

            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {

                    val successResponse = response.body()
                    val token: String = successResponse?.Token ?: ""
                    runBlocking {
                        tokenDataStore.saveToken(token)

                    }
                    Log.i("logs", "Login Succesful, auth token stored : " + token)
                    LoginResponseState.Success
                } else {
                    val apiError = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ApiErrorResponse::class.java
                    )
                    if (apiError.type == "client_error" && apiError.errors[0].code == "not_authenticated") {
                        LoginResponseState.Error.AccessDenied
                    } else {
                        LoginResponseState.Error.ServerError
                    }
                }
            } catch (e: Exception) {
                e.message?.let { Log.d("logs", "LoginRepository Error : " + it) }
                LoginResponseState.Error.ServerError
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

    override suspend fun validateAuthtoken(): Resource<Unit> = withContext(Dispatchers.IO) {
        val token = tokenDataStore.getToken()

        if (token.isNullOrEmpty()) {
            Resource.Error.AccessDenied<Unit>()
        } else {
            try {
                val response =
                    apiService.authorizeAuthToken()
                if (response.isSuccessful) {
                    Log.i("logs", "LoginRepo Auth Token validated : $token")
                    Resource.Success<Unit>()
                } else if (response.code() == 401 || response.code() == 403) { // unauthorized or forbidden
                    Resource.Error.AccessDenied<Unit>()
                } else {
                    Resource.Error.ServerError<Unit>()
                }
            } catch (e: HttpException) {
                Resource.Error.ClientError<Unit>()
            } catch (e: IOException) {
                Resource.Error.NetworkError<Unit>()
            } catch (e: Exception) {
                Log.e("logs", e.toString())
                Resource.Error.ServerError()
            }
        }
    }
}



