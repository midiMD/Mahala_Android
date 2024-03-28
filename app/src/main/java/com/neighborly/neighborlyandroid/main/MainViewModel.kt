package com.neighborly.neighborlyandroid.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
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