package com.neighborly.neighborlyandroid.login.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neighborly.neighborlyandroid.login.models.LoginRequest
import kotlinx.coroutines.launch
import com.neighborly.neighborlyandroid.login.data.LoginRepository



class LoginViewModel(private val repository: LoginRepository): ViewModel()  {
    //private val _repository: LoginRepository = LoginRepository()

    val loginState: MutableLiveData<LoginState> by lazy {
        MutableLiveData<LoginState>()
    }
    data class LoginState(
        val loading:Boolean = false,
        val authorized:Boolean = false,
        val credentialsDeclined:Boolean = false,
        val error:String? =null
    )
    fun onLoginButtonPress(email:String, password:String ) {
        loginState.value= LoginState(loading = true)
        val requestBody: LoginRequest = LoginRequest(email,password)

        viewModelScope.launch{
            val isAuthorized : Boolean = repository.makeLoginRequest(requestBody)
            if (isAuthorized){
                loginState.value= LoginState(authorized = true)
            }else{
                loginState.value= LoginState(credentialsDeclined = true)
            }
        }

    }

}
