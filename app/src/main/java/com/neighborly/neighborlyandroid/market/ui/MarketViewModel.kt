package com.neighborly.neighborlyandroid.market.ui

import android.app.Application
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
import com.neighborly.neighborlyandroid.login.data.LoginRepository
import com.neighborly.neighborlyandroid.login.models.LoginRequest
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.neighborly.neighborlyandroid.market.data.MarketRepository
import com.neighborly.neighborlyandroid.market.models.Item
import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest

import kotlinx.coroutines.launch



class MarketViewModel(private val marketRepository: MarketRepository,
                      private val savedStateHandle: SavedStateHandle
): ViewModel()  {
    // we don't wanna expose the mutable list to the outside so we make it private
    private val _marketScreenUiState: MutableLiveData<MarketScreenUiState> = MutableLiveData<MarketScreenUiState>(MarketScreenUiState())
    private val _marketItems = emptyList<Item>().toMutableStateList()
    val marketItems:List<Item>  //read only, we don't expose set() method
        get() = _marketItems
    val marketScreenUiState: LiveData<MarketScreenUiState>
        get()= _marketScreenUiState
    private val _marketActiveCategories = emptyList<Int>().toMutableStateList()
    val marketActiveCategories:List<Int>  //read only, we don't expose set() method
        get() = _marketActiveCategories
    data class MarketScreenUiState(
        val isLoading:Boolean = false,
        var sortByToggle:Boolean = false,
        val sortByValue:Int?= null,
        val categoriesToggle:Boolean = false,
        var searchQuery:String = "",
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
    fun onSearchButtonPress(query:String) {
        if (query.isNotEmpty()){
            _marketScreenUiState.value = _marketScreenUiState.value?.copy(isLoading = true)
            val requestBody: MarketSearchRequest = MarketSearchRequest(query, _marketActiveCategories)

        }

//        viewModelScope.launch{
//            val isAuthorized : Boolean = loginRepository.makeLoginRequest(requestBody)
//            if (isAuthorized){
//                loginState.value= LoginState(authorized = true)
//            }else{
//                loginState.value= LoginState(credentialsDeclined = true)
//            }
//        }

    }
    fun toggleCategories(value:Boolean) {
        _marketScreenUiState.value = _marketScreenUiState.value?.copy(categoriesToggle = value)
    }
    fun toggleSortBy(value:Boolean) {
        _marketScreenUiState.value = _marketScreenUiState.value?.copy(sortByToggle = value)

    }

}
