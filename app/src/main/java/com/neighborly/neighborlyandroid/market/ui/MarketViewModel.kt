package com.neighborly.neighborlyandroid.market.ui

import android.app.Application
import android.util.Log
import androidx.collection.intSetOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
import com.neighborly.neighborlyandroid.common.models.Category
import com.neighborly.neighborlyandroid.common.models.SortBy
import com.neighborly.neighborlyandroid.market.data.MarketRepository
import com.neighborly.neighborlyandroid.market.models.MarketItem
import com.neighborly.neighborlyandroid.market.models.MarketResponseState
import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.cacheGet
import kotlin.reflect.KProperty


class MarketViewModel(private val marketRepository: MarketRepository,
                      private val savedStateHandle: SavedStateHandle
): ViewModel()  {
    // ******** main app stuff. To DO: Separated MainViewModel passed to MainActivity
    // the screen that we are on currently
    private val _selectedNavItem= MutableStateFlow<Int>(0) // default screen is Market
    val selectedNavItem = _selectedNavItem.asStateFlow()



    // we don't wanna expose the mutable list to the outside so we make it private
    private val _uiState = MutableStateFlow<MarketScreenUiState>(MarketScreenUiState()) // what we modify in the view model
    val uiState = _uiState.asStateFlow() // expose it as read-only
    //private val _marketScreenUiState: MutableLiveData<MarketScreenUiState> = MutableLiveData<MarketScreenUiState>(MarketScreenUiState())
//    private val _itemSectionState:MutableLiveData<MarketItemSectionState> = MutableLiveData<MarketItemSectionState>(
//        MarketItemSectionState(items = emptyList(), isLoading = false, error = null))
    private val _itemSectionUiState = MutableStateFlow<MarketItemSectionState>(MarketItemSectionState())
    val itemSectionUiState = _itemSectionUiState.asStateFlow()

    private val _categoryState = MutableStateFlow<List<CategoryOption>>(Category.entries.map { category ->
        CategoryOption(category = category, isActive = false)
    })
    val categoryState = _categoryState.asStateFlow()
    private val _activeSortBy = MutableStateFlow<SortBy>(SortBy.NOTHING)
    val activeSortBy = _activeSortBy.asStateFlow()
    // Initialize the state flow with a map of all categories set to false
//    private val _categoryActiveState: MutableStateFlow<Map<Category, Boolean>> = MutableStateFlow(
//        Category.entries.associateWith { false }
//    )
//
//    // Expose as a read-only StateFlow
//    val categoryActiveState: StateFlow<Map<Category, Boolean>> = _categoryActiveState.asStateFlow()

    //val marketScreenUiState: MutableLiveData<MarketScreenUiState>
     //   get()= _marketScreenUiState
//    var activeCategories by mutableStateOf<List<Int>>(emptyList())
//        private set
    data class MarketScreenUiState(
        val sortByValue:Int?= null,
        var categoriesSelected: List<Int> = emptyList(),
        val error: String? = null
    )

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
    fun toggleCategory(category: Category,value:Boolean){
        Log.i("testDropdown","viewModel.toggleCategory | "+ category  + "to value:"+value)
        _categoryState.value = _categoryState.value.map { categoryOption ->
            if (categoryOption.category == category) {
                categoryOption.copy(isActive = value) // Modify only the matching item
            } else {
                categoryOption // Keep other items unchanged
            }
        }
        Log.i("testDropdown","category " + category+"  toggled to: "+value)

    }
    fun toggleSortByOption(option:SortBy, value:Boolean){
        if (!value){
            if (option == _activeSortBy.value){
            _activeSortBy.value = SortBy.NOTHING}
        }else{
            _activeSortBy.value = option
        }


    }
    fun onSearchButtonPress(query:String) {
        Log.i("logs","Market search button pressed")
        if (query.isNotEmpty()){
            _itemSectionUiState.value = _itemSectionUiState.value.copy(isLoading = true)
            var activeCategoryIdList:List<Int> = _categoryState.value.filter{ it.isActive }.map{it.category.id}
            val requestBody = MarketSearchRequest(query, activeCategoryIdList)
            Log.i("logs", requestBody.toString())
            //val responseState: MarketResponseState

            viewModelScope.launch {
                val responseState:Deferred<MarketResponseState> = async {marketRepository.requestMarketItems(requestBody = requestBody)}
                processResponse(responseState)
            }




        }


    }
    suspend fun processResponse(marketResponseState: Deferred<MarketResponseState>) {
        // Await the result when ready
        delay(2000) // A coroutine-friendly function that suspends for 1 second
        val responseState = marketResponseState.await()  // This suspends until fetchData completes
        _itemSectionUiState.value = _itemSectionUiState.value.copy(isLoading = false)
        if (responseState.isSuccess){
            if (!responseState.data.isNullOrEmpty()){
                _itemSectionUiState.value = _itemSectionUiState.value.copy(items= responseState.data!!)
                Log.i("logs","MarketViewModel: Market items request succesful with response data = " + responseState.data)
            }

        }else{
            Log.i("logs","MarketViewModel:Market items request was unsuccesfull")
            _uiState.value= _uiState.value.copy(error = responseState.error)

        }

    }


}

data class CategoryOption(
    val category: Category,
    val isActive:Boolean = false,
)
data class MarketItemSectionState(
    var items:List<MarketItem> = emptyList<MarketItem>(),
    var isLoading:Boolean = false,
    var errorMessages: List<String> = emptyList<String>()
)

