package com.neighborly.neighborlyandroid.login.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.login.data.LoginRepository
import com.neighborly.neighborlyandroid.login.models.LoginRequest
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY

import kotlinx.coroutines.launch



class LoginViewModel(private val loginRepository:LoginRepository,
        private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    val loginState: MutableLiveData<LoginState> by lazy {
        MutableLiveData<LoginState>()
    }
    data class LoginState(
        val loading:Boolean = false,
        val authorized:Boolean = false,
        val credentialsDeclined:Boolean = false,
        val error:String? =null
    )
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
    fun onLoginButtonPress(email:String, password:String ) {
        loginState.value= LoginState(loading = true)
        val requestBody: LoginRequest = LoginRequest(email,password)

        viewModelScope.launch{
            val isAuthorized : Boolean = loginRepository.makeLoginRequest(requestBody)
            if (isAuthorized){
                loginState.value= LoginState(authorized = true)
            }else{
                loginState.value= LoginState(credentialsDeclined = true)
            }
        }

    }

}
