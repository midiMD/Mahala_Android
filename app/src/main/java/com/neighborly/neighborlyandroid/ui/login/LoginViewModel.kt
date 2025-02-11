package com.neighborly.neighborlyandroid.ui.login

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
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.UserStatus
import com.neighborly.neighborlyandroid.domain.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch




class LoginViewModel(private val loginRepository: LoginRepository,
                     private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    private val _uiState = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only

    init {
        Log.d("logs","LoginViewModel instantiated")
        validateAuthToken()
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val loginRepository =
                    (this[APPLICATION_KEY]  as BaseApplication).appCompositionRoot.loginRepository
                LoginViewModel(
                    loginRepository = loginRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
    fun toggleIdle(){
        _uiState.value = LoginScreenState.Idle
    }
    fun validateAuthToken(){
        _uiState.value = LoginScreenState.Loading
        viewModelScope.launch{
            val apiResponse = loginRepository.validateAuthtoken()
            when(apiResponse){
                is Resource.Error.ClientError,
                is Resource.Error.NetworkError -> {
                    _uiState.value = LoginScreenState.Error(message = "Check your intenrnet connection")
                }
                is Resource.Success -> {
                    val isVerifiedAddress:Boolean = apiResponse.data!!.isAddressVerified
                    if (isVerifiedAddress){
                        _uiState.value = LoginScreenState.AddressVerified
                    }else{
                     _uiState.value = LoginScreenState.AddressNotVerified
                    }
                }

                else -> {
                    _uiState.value = LoginScreenState.Idle
                }
            }
        }
    }
    fun onLoginButtonPress(email:String, password:String ) {
        if (!(email.isEmpty() || password.isEmpty())){
            _uiState.value = LoginScreenState.Loading
            viewModelScope.launch{
                val apiResponse: Resource<UserStatus> = loginRepository.login(email.trim().lowercase(),password)
                when(apiResponse){
                    is Resource.Error.AccessDenied ->{_uiState.value = LoginScreenState.Error("Invalid Credentials")}
                    is Resource.Error.NetworkError -> {_uiState.value = LoginScreenState.Error("Check your Internet Connection")}
                    is Resource.Error.ClientError,
                    is Resource.Error.ServerError -> {_uiState.value = LoginScreenState.Error("Something went wrong. Try again")}
                    is Resource.Success -> {
                            val isVerifiedAddress:Boolean = apiResponse.data!!.isAddressVerified
                            if (isVerifiedAddress){
                                _uiState.value = LoginScreenState.AddressVerified
                            }else{
                                _uiState.value = LoginScreenState.AddressNotVerified
                            }
                    }
                }
            }
        }else{
            _uiState.value = LoginScreenState.Error("Fill all fields.")
        }

    }


}
