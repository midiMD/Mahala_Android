package com.neighborly.neighborlyandroid.ui.register

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.neighborly.neighborlyandroid.common.RegisterResponseState

import com.neighborly.neighborlyandroid.domain.model.RegistrationDetails
import com.neighborly.neighborlyandroid.data.repository.RegisterRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch

fun validateDetailsLocally(details: RegistrationDetails): RegistrationScreenState? {
    // Validate the input before making an API request. Details are modified in place

    // Trim and lowercase all fields in place
    details.apply {
        fullName = fullName.trim().lowercase()
        email = email.trim().lowercase()
        password = password.trim()
        street = street.trim().lowercase()
        number = number.trim().lowercase()
        postcode = postcode.trim().lowercase()
        apartmentNumber = apartmentNumber.trim().lowercase()
    }

    // Check if terms and conditions are accepted
    if (!details.isCheckedTAndC) {
        return RegistrationScreenState.Error("Must agree to T&C")
    }

    // Check for missing fields
    val missingFields = mutableListOf<String>()
    if (details.fullName.isEmpty()) missingFields.add("Full Name")
    if (details.email.isEmpty()) missingFields.add("Email")
    if (details.password.isEmpty()) missingFields.add("Password")
    if (details.street.isEmpty()) missingFields.add("Street")
    if (details.number.isEmpty()) missingFields.add("Number")
    if (details.postcode.isEmpty()) missingFields.add("Postcode")

    if (missingFields.isNotEmpty()) {
        return RegistrationScreenState.MissingFields(missingFields)
    }

    // Basic email validation
    if (!details.email.contains("@")) {
        return RegistrationScreenState.Error("Invalid email address")
    }

    // If all checks pass, return null
    return null
}
class RegisterViewModel(private val registerRepository: RegisterRepositoryImpl,
                        private val savedStateHandle: SavedStateHandle
): ViewModel()  {


    private val _uiState = MutableStateFlow<RegistrationScreenState>(RegistrationScreenState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only
    fun toggleIdle(){
        _uiState.value = RegistrationScreenState.Idle
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val registerRepository =
                    (this[APPLICATION_KEY]  as BaseApplication).appCompositionRoot.registerRepository
                RegisterViewModel(
                    registerRepository = registerRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
    fun onRegisterButtonPress( details:RegistrationDetails ) {

        _uiState.value = RegistrationScreenState.Loading
        val localValidationResult:RegistrationScreenState? = validateDetailsLocally(details)
        Log.d("logs","local validation results " + localValidationResult.toString())
        if (localValidationResult == null){
            //
            // make API request
            viewModelScope.launch{
                val apiResponseState: RegisterResponseState = registerRepository.register(details)
                when(apiResponseState){
                    RegisterResponseState.Success -> {
                        _uiState.value = RegistrationScreenState.Success
                    }

                    RegisterResponseState.Error.EmailExists -> {
                        _uiState.value = RegistrationScreenState.Error("This email exists. Please log in or Forgot Password")
                    }
                    RegisterResponseState.Error.InvalidHouseError -> {
                        _uiState.value = RegistrationScreenState.Error("House Address is invalid")
                    }
                    RegisterResponseState.Error.NetworkError -> {
                        _uiState.value = RegistrationScreenState.Error("Could not connect. Check your Internet")
                    }
                    RegisterResponseState.Error.ClientError,
                    RegisterResponseState.Error.ServerError -> {
                        _uiState.value = RegistrationScreenState.Error("Something went wrong. Try again")
                    }

                }

            }
        }else{
            _uiState.value = localValidationResult
        }


    }

}
