package com.neighborly.neighborlyandroid.ui.register

sealed class RegistrationScreenState{
    data object Idle : RegistrationScreenState()
    data object Success: RegistrationScreenState()
    data object Loading: RegistrationScreenState()
    //Error
    data class MissingFields(val fields:List<String>):RegistrationScreenState()
    data class Error(val message:String):RegistrationScreenState()
//    sealed class Error: RegistrationScreenState(){
//        data class MissingFields(val fields:List<String>):Error()
//
//    }

}