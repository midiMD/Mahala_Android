package com.neighborly.neighborlyandroid.ui.login

sealed class LoginScreenState{
    data object Idle : LoginScreenState()
    data object Success: LoginScreenState()
    data object Loading: LoginScreenState()
    //Error
    sealed class Error: LoginScreenState(){
        data object InvalidCredentials:Error()
        data object MissingFields:Error()
        data object InvalidEmail:Error()
        data object NetworkError:Error()
    }

}