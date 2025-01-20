package com.neighborly.neighborlyandroid.ui.login.reset


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.createSavedStateHandle

import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.domain.repository.LoginRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.common.ResetPasswordResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PasswordResetViewModel(
    private val loginRepository: LoginRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<ResetScreenState>(ResetScreenState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only

    fun onClickReset(email: String) {
        _uiState.value = ResetScreenState.Loading
        if(email.trim().isEmpty() || !email.contains("@")){
            _uiState.value = ResetScreenState.Error("Enter a valid email")
        }
        else{
            Log.d("logs","Making reset password call")
            viewModelScope.launch {
                try {
                    // Add your logic to reset the password
                    val apiResponseState: ResetPasswordResponseState =loginRepository.resetPassword(email)

                    when(apiResponseState){
                        ResetPasswordResponseState.Error.ServerError,
                        ResetPasswordResponseState.Error.ClientError -> {_uiState.value = ResetScreenState.Error("Something went wrong. Try again ")}
                        ResetPasswordResponseState.Error.NetworkError -> {_uiState.value = ResetScreenState.Error("Something went wrong. Check internet")}
                        ResetPasswordResponseState.Success -> {_uiState.value = ResetScreenState.Success}
                    }
                } catch (e: Exception) {
                    Log.e("network","ResetViewModel reset cal: $e")
                    _uiState.value = ResetScreenState.Error("Something went wrong. Please try again")
                }
            }
        }
    }
    fun toggleIdle(){
        _uiState.value = ResetScreenState.Idle
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val loginRepository =
                    (this[APPLICATION_KEY] as BaseApplication).appCompositionRoot.loginRepository
                PasswordResetViewModel(
                    loginRepository = loginRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}
