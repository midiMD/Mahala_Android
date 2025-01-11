package com.neighborly.neighborlyandroid.ui.inventory.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.neighborly.neighborlyandroid.BaseApplication

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.neighborly.neighborlyandroid.domain.model.AddItemDetails
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


sealed class AddInventoryScreenUiState{
    data object Idle : AddInventoryScreenUiState()
    data object Success:AddInventoryScreenUiState()
    data object Loading:AddInventoryScreenUiState()
    //Error
    sealed class Error:AddInventoryScreenUiState(){
        data object MissingFields:Error()
        data object NetworkError:Error()
    }

}



class AddInventoryViewModel(private val inventoryRepository: InventoryRepository,
                            private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    private val _uiState = MutableStateFlow<AddInventoryScreenUiState>(AddInventoryScreenUiState.Idle) // what we modify in the view model
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
        _uiState.value = AddInventoryScreenUiState.Idle
    }
    fun onAddButtonPress(itemDetails: AddItemDetails) {


    }


}
