package com.neighborly.neighborlyandroid.common

import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.MarketItem


sealed class ResetPasswordResponseState{
    data object Success: ResetPasswordResponseState()
    sealed class Error: ResetPasswordResponseState(){
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

sealed class PasswordChangeResponseState{
    data object Success: PasswordChangeResponseState()
    sealed class Error: PasswordChangeResponseState(){
        data object IncorrectPassword: Error()
        data object AccessDenied:Error()
        data object ServerError: Error() // error on server side
        data object ClientError: Error() // error on client side
        data object NetworkError: Error()
    }
}

