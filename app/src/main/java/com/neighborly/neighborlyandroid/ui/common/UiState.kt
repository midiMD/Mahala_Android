package com.neighborly.neighborlyandroid.ui.common

sealed class UiState<out T>{
    data object Loading:UiState<Nothing>()
    data class Success<T>(val data:T):UiState<T>()
    sealed class Error : UiState<Nothing>() {
        data class ClientError(val message: String) : Error()
        data class ServerError(val message: String) : Error()
        data class NetworkError(val message: String) : Error()
        data class GenericError(val message: String) : Error()
    }
}
