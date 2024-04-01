package com.neighborly.neighborlyandroid.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication
import com.neighborly.neighborlyandroid.login.ui.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository,
    private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val mainRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]  as BaseApplication).appCompositionRoot.mainRepository
                MainViewModel(
                    repository = mainRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
    init {
        Log.d("MainViewModel","adsasd")
        viewModelScope.launch{
            if (repository.validateToken()) {
                _uiState.value = UiState.LaunchMarket
            } else {
                Log.d("logger","launching login")
                _uiState.value = UiState.LaunchLogin
            }
        }

    }

    sealed class UiState {
        object Loading : UiState()
        object LaunchLogin : UiState()
        object LaunchMarket : UiState()
    }
}