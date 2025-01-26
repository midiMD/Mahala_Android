package com.neighborly.neighborlyandroid.ui.inventory.add

import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.neighborly.neighborlyandroid.common.Resource
import com.neighborly.neighborlyandroid.domain.model.AddItemDetails
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository
import com.neighborly.neighborlyandroid.ui.market.MarketScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class AddInventoryScreenState{
    data object Idle : AddInventoryScreenState()
    data object Success:AddInventoryScreenState()
    data object Loading:AddInventoryScreenState()
    data object NavigateToInventoryHome:AddInventoryScreenState()
    //Error
    data class Error(val message:String):AddInventoryScreenState()

}



class AddInventoryViewModel(private val inventoryRepository: InventoryRepository,
                            private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    private val _uiState = MutableStateFlow<AddInventoryScreenState>(AddInventoryScreenState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val inventoryRepository =
                    (this[APPLICATION_KEY]  as BaseApplication).appCompositionRoot.inventoryRepository
                AddInventoryViewModel(
                    inventoryRepository = inventoryRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
    fun toggleIdle(){
        _uiState.value = AddInventoryScreenState.Idle
    }
    fun toggleNavigation(){
        _uiState.value = AddInventoryScreenState.NavigateToInventoryHome
    }

    fun submit(context: Context, itemDetails: AddItemDetails){
        Log.i("logs","Uri of uploaded image: ${itemDetails.thumbnailUri}")
        _uiState.value = AddInventoryScreenState.Loading
        viewModelScope.launch {
            val responseState = inventoryRepository.addItem(context,itemDetails)
            when (responseState){
                is Resource.Error.AccessDenied -> {_uiState.value = AddInventoryScreenState.Error(message = "Something went wrong. Log in again")}
                is Resource.Error.ClientError -> {_uiState.value = AddInventoryScreenState.Error(message = "Something went wrong. Please try again")}
                is Resource.Error.NetworkError -> {_uiState.value = AddInventoryScreenState.Error(message = "Something went wrong. Check your internet")}
                is Resource.Error.ServerError -> {_uiState.value = AddInventoryScreenState.Error(message = "Something went wrong. Try again")}
                is Resource.Success -> {
                    _uiState.value = AddInventoryScreenState.Success
                }
            }
        }


    }

}
