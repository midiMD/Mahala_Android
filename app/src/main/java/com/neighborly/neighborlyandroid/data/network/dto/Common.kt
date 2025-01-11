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