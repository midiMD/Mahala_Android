package com.neighborly.neighborlyandroid.ui.login

sealed class LoginScreenState{
    data object Idle : LoginScreenState()
    data object Success: LoginScreenState()
    data object Loading: LoginScreenState()
    //Error
    data class Error(val message:String): LoginScreenState()

}