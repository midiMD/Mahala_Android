package com.neighborly.neighborlyandroid.ui.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.common.PasswordChangeResponseState
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository
import com.neighborly.neighborlyandroid.domain.repository.SettingsRepository
import com.neighborly.neighborlyandroid.ui.inventory.view.InventoryItemDetailScreenState
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryScreenState
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SettingsScreenState{
    data object Idle : SettingsScreenState()
    data object Logout: SettingsScreenState()
    data object Loading: SettingsScreenState()
    data object Success:SettingsScreenState()
    //Error
    data class Error(val message:String): SettingsScreenState()

}

class SettingsViewModel(private val repository: SettingsRepository,
                        private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow<SettingsScreenState>(SettingsScreenState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val repository =
                    (this[APPLICATION_KEY] as BaseApplication).appCompositionRoot.settingsRepository
                SettingsViewModel(
                    repository = repository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
    fun logout(){
        _uiState.value = SettingsScreenState.Loading
        viewModelScope.launch {
            val responseState: Resource<Unit> = repository.logout()
            when (responseState){
                is Resource.Error.AccessDenied -> {_uiState.value =SettingsScreenState.Logout}
                is Resource.Error.ClientError -> {_uiState.value = SettingsScreenState.Error(message = "Something went wrong. Please try again")}
                is Resource.Error.NetworkError -> {_uiState.value = SettingsScreenState.Error(message = "Something went wrong. Check your internet")}
                is Resource.Error.ServerError -> {_uiState.value = SettingsScreenState.Error(message = "Something went wrong. Try again")}
                is Resource.Success -> {
                    _uiState.value = SettingsScreenState.Logout
                }
            }
        }
    }
    fun changePassword(oldPassword:String, newPassword:String){
        _uiState.value = SettingsScreenState.Loading
        viewModelScope.launch {
            val responseState = repository.passwordChange(oldPassword,newPassword)
            when (responseState){
                PasswordChangeResponseState.Error.AccessDenied -> {
                    _uiState.value = SettingsScreenState.Error("Password change failed ")
                    delay(1000)
                    _uiState.value = SettingsScreenState.Logout}
                PasswordChangeResponseState.Error.ServerError,
                PasswordChangeResponseState.Error.ClientError -> {
                    _uiState.value = SettingsScreenState.Error(message = "Something went wrong. Please try again")
                }
                PasswordChangeResponseState.Error.IncorrectPassword -> {
                    _uiState.value = SettingsScreenState.Error("Incorrect password. Try again or log out and reset.")
                }
                PasswordChangeResponseState.Error.NetworkError -> {
                    SettingsScreenState.Error(message = "Something went wrong. Check your internet")
                }

                PasswordChangeResponseState.Success -> {
                    _uiState.value = SettingsScreenState.Success
                    delay(400)
                    _uiState.value = SettingsScreenState.Logout
                }
            }
        }
    }
}