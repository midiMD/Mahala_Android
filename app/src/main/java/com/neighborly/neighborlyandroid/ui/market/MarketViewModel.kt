package com.neighborly.neighborlyandroid.ui.market

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
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.domain.model.MarketQuery

import com.neighborly.neighborlyandroid.domain.repository.MarketRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
sealed class MarketScreenState{
    data object Idle : MarketScreenState()
    data object Success: MarketScreenState()
    data object Loading: MarketScreenState()
    //Error
    data class Error(val message:String): MarketScreenState()

}
sealed class MarketItemDetailScreenState{
    data object Idle:MarketItemDetailScreenState()
    //Error
    data class Error(val message:String): MarketItemDetailScreenState()

}

class MarketViewModel(private val marketRepository: MarketRepository,
                      private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    private val _itemsState = MutableStateFlow<List<MarketItem>>(emptyList()) // what we modify in the view model
    val itemsState = _itemsState.asStateFlow() // expose it as read-only
    // we don't wanna expose the mutable list to the outside so we make it private
    private val _uiState = MutableStateFlow<MarketScreenState>(MarketScreenState.Idle) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only
// The detail
    private val _itemDetailState = MutableStateFlow<MarketItemDetail>(MarketItemDetail())
    val itemDetailState = _itemDetailState.asStateFlow() // expose it as read-only
    private val _detailScreenState = MutableStateFlow<MarketItemDetailScreenState>(MarketItemDetailScreenState.Idle)
    val detailScreenState = _detailScreenState.asStateFlow() // expose it as read-only

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val marketRepository =
                    (this[APPLICATION_KEY]  as BaseApplication).appCompositionRoot.marketRepository
                MarketViewModel(
                    marketRepository = marketRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
    fun getItemDetail(itemId:Long){
        _detailScreenState.value = MarketItemDetailScreenState.Idle
        Log.i("logs","MarketViewModel getItemDetail")
        viewModelScope.launch {
            val responseState:Resource<MarketItemDetail> = marketRepository.getItemDetail(itemId)
            when (responseState){
                is Resource.Error.AccessDenied -> {_detailScreenState.value = MarketItemDetailScreenState.Error(message = "Couldn't fetch detail. Try again.")}
                is Resource.Error.ClientError -> {_detailScreenState.value = MarketItemDetailScreenState.Error(message = "Something went wrong. Please try again")}
                is Resource.Error.NetworkError -> {_detailScreenState.value = MarketItemDetailScreenState.Error(message = "Something went wrong. Check your internet")}
                is Resource.Error.ServerError -> {_detailScreenState.value = MarketItemDetailScreenState.Error(message = "Something went wrong. Try again")}
                is Resource.Success -> {
                    _itemDetailState.value = responseState.data ?: MarketItemDetail()
                }
            }
        }

    }

    fun onSearch(query:String, activeCategoryIdList:List<Int>, activeSortBy:Int) {
        Log.i("logs","Market search button pressed")
        _uiState.value = MarketScreenState.Loading
        val marketQuery:MarketQuery  = MarketQuery(
            searchQuery = query,
            filterCategoryIds = activeCategoryIdList.toSet()
        )
        viewModelScope.launch {
            val responseState:Deferred<Resource<List<MarketItem>>> = async {marketRepository.searchMarketItems(searchQuery = marketQuery)}
            processResponse(responseState)
        }

    }
    suspend fun processResponse(marketResponseState: Deferred<Resource<List<MarketItem>>>) {
        // Await the result when ready
        delay(2000) // A coroutine-friendly function that suspends for 1 second
        val responseState = marketResponseState.await()  // This suspends until fetchData completes
        _uiState.value = MarketScreenState.Loading
        when (responseState){
            is Resource.Error.AccessDenied -> {_uiState.value = MarketScreenState.Error(message = "Something went wrong. Log in again")}
            is Resource.Error.ClientError -> {_uiState.value = MarketScreenState.Error(message = "Something went wrong. Please try again")}
            is Resource.Error.NetworkError -> {_uiState.value = MarketScreenState.Error(message = "Something went wrong. Check your internet")}
            is Resource.Error.ServerError -> {_uiState.value = MarketScreenState.Error(message = "Something went wrong. Try again")}
            is Resource.Success -> {
                _uiState.value = MarketScreenState.Idle
                _itemsState.value = responseState.data ?: emptyList()
            }
        }

    }


}

data class CategoryOption(
    val category: Category,
    val isActive:Boolean = false,
)


