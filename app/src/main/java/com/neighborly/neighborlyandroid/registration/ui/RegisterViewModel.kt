package com.neighborly.neighborlyandroid.registration.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.neighborly.neighborlyandroid.common.models.House
import com.neighborly.neighborlyandroid.registration.data.UserRegisterApiResponseState
import com.neighborly.neighborlyandroid.registration.data.RegisterRepository
import com.neighborly.neighborlyandroid.registration.models.UserRegisterApiResponse

import kotlinx.coroutines.launch



class RegisterViewModel(private val registerRepository: RegisterRepository,
                        private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    val registerPageState: MutableLiveData<RegisterPageState> by lazy {
        MutableLiveData<RegisterPageState>()
    }
    data class RegisterPageState(
        val loading:Boolean = false,
        val accepted:Boolean = false,
        val emailAlreadyExists:Boolean = false,
        val errors:List<UserRegisterApiResponse.ErrorDetails> ? =null,
        val goToLogin:Boolean= false,
    )
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
    fun onRegisterButtonPress( email:String,password:String,fullName:String, ) {
        registerPageState.value= RegisterPageState(loading = true)

        viewModelScope.launch{
            val apiResponse:UserRegisterApiResponseState   = registerRepository.makeRegisterRequest(email,password,fullName)
            registerPageState.value = RegisterPageState(accepted =apiResponse.accepted, emailAlreadyExists = apiResponse.emailAlreadyExists, errors = apiResponse.errors )
        }

    }
    fun onBackButtonPress(){
        registerPageState.value= RegisterPageState(loading = true)

    }

}
