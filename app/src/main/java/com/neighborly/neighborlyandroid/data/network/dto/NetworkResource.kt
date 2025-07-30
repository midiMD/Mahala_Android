package com.neighborly.neighborlyandroid.data.network.dto

data class ApiErrorResponse(
    val type:String,
    val errors:List<ResponseErrorDetails>,
)
data class ResponseErrorDetails(
    val code:String,
    val detail:String,
    val attr:String
)

// from the remote, this is not exposed to the UI layer
sealed class NetworkResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): NetworkResource<T>(data)
    class Error<T>(message: String, data: T? = null): NetworkResource<T>(data, message)
}