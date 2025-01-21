package com.neighborly.neighborlyandroid.ui.inventory.view

import android.util.Log
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
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.domain.repository.InventoryRepository
import com.neighborly.neighborlyandroid.ui.market.MarketItemDetailScreenState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class ViewInventoryScreenState{
    data object Idle : ViewInventoryScreenState()
    data object Success: ViewInventoryScreenState()
    data object Loading: ViewInventoryScreenState()
    //Error
    data class Error(val message:String): ViewInventoryScreenState()

}
sealed class InventoryItemDetailScreenState{
    data object Idle:InventoryItemDetailScreenState()
    //Error
    data class Error(val message:String): InventoryItemDetailScreenState()

}



class ViewInventoryViewModel(private val inventoryRepository: InventoryRepository,
                             private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    private val _uiState = MutableStateFlow<ViewInventoryScreenState>(ViewInventoryScreenState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only
    // items shown in the list of inventory items
    private val _itemsState = MutableStateFlow<List<InventoryItem>>(emptyList()) // what we modify in the view model
    val itemsState = _itemsState.asStateFlow() // expose it as read-only

    private val _itemDetailState = MutableStateFlow<InventoryItemDetail>(InventoryItemDetail())
    val itemDetailState = _itemDetailState.asStateFlow() // expose it as read-only
    private val _detailScreenState = MutableStateFlow<InventoryItemDetailScreenState>(
        InventoryItemDetailScreenState.Idle)
    val detailScreenState = _detailScreenState.asStateFlow() // expose it as read-only
    init{
        getItems()
    }

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
    fun getItems(){
        _uiState.value = ViewInventoryScreenState.Loading
        Log.d("logs","ViewInventoryViewModel getItems")
        viewModelScope.launch {
            val responseState: Deferred<Resource<List<InventoryItem>>> = async {inventoryRepository.getItems()}
            processResponse(responseState)
        }
    }
    private suspend fun processResponse(response:Deferred<Resource<List<InventoryItem>>>){
        when (val responseState = response.await()){  // This suspends until fetchData completes
            is Resource.Error.AccessDenied -> {_uiState.value = ViewInventoryScreenState.Error(message = "Couldn't verify credentials. Please Log in again")}
            is Resource.Error.ClientError -> {_uiState.value = ViewInventoryScreenState.Error(message = "Something went wrong. Please try again")}
            is Resource.Error.NetworkError -> {_uiState.value = ViewInventoryScreenState.Error(message = "Something went wrong. Check your internet")}
            is Resource.Error.ServerError -> {_uiState.value = ViewInventoryScreenState.Error(message = "Something went wrong. Try again")}
            is Resource.Success -> {
                _uiState.value = ViewInventoryScreenState.Idle
                _itemsState.value = responseState.data ?: emptyList<InventoryItem>()
            }
        }

    }
    fun getItemDetail(itemId:Long){
        _detailScreenState.value = InventoryItemDetailScreenState.Idle
        Log.i("logs","ViewInventoryViewModel getItemDetail")
        viewModelScope.launch {
            val responseState: Resource<InventoryItemDetail> = inventoryRepository.getItemDetail(itemId)
            when (responseState){
                is Resource.Error.AccessDenied -> {_detailScreenState.value = InventoryItemDetailScreenState.Error(message = "Couldn't fetch detail. Try again.")}
                is Resource.Error.ClientError -> {_detailScreenState.value = InventoryItemDetailScreenState.Error(message = "Something went wrong. Please try again")}
                is Resource.Error.NetworkError -> {_detailScreenState.value = InventoryItemDetailScreenState.Error(message = "Something went wrong. Check your internet")}
                is Resource.Error.ServerError -> {_detailScreenState.value = InventoryItemDetailScreenState.Error(message = "Something went wrong. Try again")}
                is Resource.Success -> {
                    _itemDetailState.value = responseState.data ?: InventoryItemDetail()
                }
            }
        }

    }



}
