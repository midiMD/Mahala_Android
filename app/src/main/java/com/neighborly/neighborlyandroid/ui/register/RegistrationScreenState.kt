package com.neighborly.neighborlyandroid.ui.register

import com.neighborly.neighborlyandroid.ui.common.UiState

sealed class RegistrationScreenState{
    data object Idle : RegistrationScreenState()
    data object Success: RegistrationScreenState()
    data object Loading: RegistrationScreenState()
    //Error
    data class MissingFields(val fields:List<String>):RegistrationScreenState()
    data class Error(val message:String):RegistrationScreenState()


}