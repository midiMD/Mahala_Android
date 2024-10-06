package com.neighborly.neighborlyandroid.market.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
import com.neighborly.neighborlyandroid.market.models.MarketSearchRequest

import kotlinx.coroutines.launch



class MarketViewModel(private val marketRepository: MarketRepository,
                      private val savedStateHandle: SavedStateHandle
): ViewModel()  {

    val marketState: MutableLiveData<MarketState> by lazy {
        MutableLiveData<MarketState>()
    }
    data class MarketState(
        var loading:Boolean = false,
        var categoriesViewShow: Boolean=false,
        var sortByViewShow:Boolean = false,

        val error:String? =null,
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
    fun onSearchButtonPress(query:String,category:Int) {
        marketState.value= MarketState(loading = true)
        val requestBody: MarketSearchRequest = MarketSearchRequest(query, category)

//        viewModelScope.launch{
//            val isAuthorized : Boolean = loginRepository.makeLoginRequest(requestBody)
//            if (isAuthorized){
//                loginState.value= LoginState(authorized = true)
//            }else{
//                loginState.value= LoginState(credentialsDeclined = true)
//            }
//        }

    }
    fun onCategoryButtonPress() {
        val currentState = marketState.value
        if (currentState != null) {
            currentState.categoriesViewShow = !currentState.categoriesViewShow
        }
        marketState.value = currentState
    }
    fun onSortByButtonPress() {
        val currentState = marketState.value
        if (currentState != null) {
            currentState.sortByViewShow = !currentState.sortByViewShow
        }
        marketState.value = currentState
    }

}
