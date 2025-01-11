package com.neighborly.neighborlyandroid.ui.register

sealed class RegistrationScreenState{
    data object Idle : RegistrationScreenState()
    data object Success: RegistrationScreenState()
    data object Loading: RegistrationScreenState()
    //Error
    sealed class Error: RegistrationScreenState(){
        data class MissingFields(val fields:List<String>):Error()
        data object UnCheckedTAndC:Error()
        data object EmailExists:Error()
        data object WeakPassword:Error()
        data object InvalidEmail:Error()
        data object ServerError:Error()
        data object NetworkError:Error()
        data object InvalidHouseError:Error()
    }

}