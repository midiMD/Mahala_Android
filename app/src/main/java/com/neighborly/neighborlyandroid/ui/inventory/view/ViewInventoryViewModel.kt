package com.neighborly.neighborlyandroid.ui.inventory.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


sealed class ViewInventoryScreenUiState{
    data object Idle : ViewInventoryScreenUiState()
    data object Success:ViewInventoryScreenUiState()
    data object Loading:ViewInventoryScreenUiState()
    //Error
    sealed class Error:ViewInventoryScreenUiState(){
        data object NetworkError:Error()
    }

}



class ViewInventoryViewModel(private val inventoryRepository: InventoryRepository,
                             private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    private val _uiState = MutableStateFlow<ViewInventoryScreenUiState>(ViewInventoryScreenUiState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val inventoryRepository =
                    (this[APPLICATION_KEY]  as BaseApplication).appCompositionRoot.inventoryRepository
                ViewInventoryViewModel(
                    inventoryRepository = inventoryRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }



}
