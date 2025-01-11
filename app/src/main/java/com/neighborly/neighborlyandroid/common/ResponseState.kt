package com.neighborly.neighborlyandroid.common

import com.neighborly.neighborlyandroid.domain.model.MarketItem

//
sealed class MarketItemResponseState{
    class Success(data: List<MarketItem>): MarketItemResponseState()
    sealed class Error: MarketItemResponseState(){
        data object AccessDenied: Error()
        data object ServerError: Error() // error on server side
        data object ClientError: Error() // error on client side
    }

}

sealed class LoginResponseState{
    data object Success: LoginResponseState()
    sealed class Error: LoginResponseState(){
        data object AccessDenied: Error()
        data object ServerError: Error() // error on server side
        data object ClientError: Error() // error on client side
        data object NetworkError: Error() // e.g. internet buggin
    }

}
sealed class RegisterResponseState{
    data object Success: RegisterResponseState()
    sealed class Error: RegisterResponseState(){
        data object EmailExists: Error()
        data object ServerError: Error() // error on server side
        data object ClientError: Error() // error on client side
        data object NetworkError: Error()
        data object InvalidHouseError: Error()
    }
}
