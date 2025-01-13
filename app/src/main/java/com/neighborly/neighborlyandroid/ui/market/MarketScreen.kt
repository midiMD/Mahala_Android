package com.neighborly.neighborlyandroid.ui.market

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldDefaults
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.SortBy
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.login.LoginViewModel
import com.neighborly.neighborlyandroid.ui.market.components.MarketItemsSection
import com.neighborly.neighborlyandroid.ui.market.components.MarketTopBar
import com.neighborly.neighborlyandroid.ui.market.item_detail.MarketItemDetailScreen
import com.neighborly.neighborlyandroid.ui.navigation.Screen
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import kotlinx.coroutines.launch

// ! viewModel lifecycle will be that of MarketActivity > lifecycle of MarketScreen composable
// View model

@Composable
private fun MarketListScreen(
    itemsState:List<MarketItem>,
    uiState:MarketScreenState,
    snackbarHostState: SnackbarHostState,
    onSearch: (query:String,activeCategoryIdList:List<Int>,activeSortBy:Int)-> Unit,
    navigateToMarketItemDetail: (item: MarketItem) -> Unit,
    //activeSortByState:SortBy,
    //categoryState:List<CategoryOption>,
    modifier:Modifier = Modifier
){
    var categoryState: List<CategoryOption> by remember { mutableStateOf(Category.entries.map { category ->
        CategoryOption(category=category, isActive = false)
    }) }
    var activeSortByState:SortBy by remember {mutableStateOf(SortBy.NOTHING)}

    fun toggleCategory(category: Category, value:Boolean){
        categoryState = categoryState.map { categoryOption ->
            if (categoryOption.category == category) {
                categoryOption.copy(isActive = value) // Modify only the matching item
            } else {
                categoryOption // Keep other items unchanged
            }
        }
    }
    fun toggleSortByOption(option: SortBy, value:Boolean){
        if (!value){
            if (option == activeSortByState){
                activeSortByState = SortBy.NOTHING}
        }else{
            activeSortByState = option
        }
    }
    fun onSearchButton(query:String){
        val activeCategoryIdList:List<Int> = categoryState.filter{ it.isActive }.map{it.category.id}
        onSearch(query,activeCategoryIdList,activeSortByState.id)
    }

    Column(modifier = modifier) {
        MarketTopBar(modifier = modifier,
            onSearch = {query->onSearchButton(query)},
            onToggleSortByOptions = { option: SortBy, value:Boolean -> toggleSortByOption(option, value)},
            sortByOptions = SortBy.entries,
            activeSortBy = activeSortByState,
            categoryState = categoryState,
            onToggleCategories = {category, value ->toggleCategory(category,value)}
        )
        when (uiState){
            is MarketScreenState.Error -> {
                MarketItemsSection(modifier = Modifier.background(color = MaterialTheme.colorScheme.background), itemsState = itemsState, onClickItem = {navigateToMarketItemDetail(it)})
                LaunchedEffect(uiState) {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }

            MarketScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            MarketScreenState.Idle,
            MarketScreenState.Success -> {
                MarketItemsSection(modifier = Modifier.background(color = MaterialTheme.colorScheme.background), itemsState = itemsState, onClickItem = {navigateToMarketItemDetail(it)})

            }
        }

    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MarketScreen(modifier: Modifier = Modifier,
                 marketViewModel: MarketViewModel = viewModel(factory = MarketViewModel.Factory),
                 navigateToChat:()->Unit) {
    val itemsState by marketViewModel.itemsState.collectAsState()
    val uiState by marketViewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>(
        scaffoldDirective = calculatePaneScaffoldDirective(currentWindowAdaptiveInfo()),
        adaptStrategies = ListDetailPaneScaffoldDefaults.adaptStrategies(),
    )
    val coroutineScope = rememberCoroutineScope()

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                MarketListScreen(itemsState,uiState,snackbarHostState,
                    onSearch = {query, activeCategoryIdList, activeSortBy ->  marketViewModel.onSearch(query,activeCategoryIdList,activeSortBy)},
                    navigateToMarketItemDetail={item:MarketItem ->
                        coroutineScope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail, item
                            )
                        }
                    })
            }

                   },
        detailPane = {
            val content = navigator.currentDestination?.contentKey
            if (content != null){
                val marketItem: MarketItem =  content as MarketItem
                val itemDetail:MarketItemDetail by marketViewModel.itemDetailState.collectAsStateWithLifecycle()
                val itemDetailScreenState:MarketItemDetailScreenState by marketViewModel.detailScreenState.collectAsStateWithLifecycle()
                marketViewModel.getItemDetail(marketItem.id)
                LaunchedEffect(itemDetailScreenState) {
                    when(itemDetailScreenState){
                        is MarketItemDetailScreenState.Error -> {
                            snackbarHostState.showSnackbar((itemDetailScreenState as MarketItemDetailScreenState.Error).message)
//                            marketViewModel.toggleIdleDetailScreen()
                        }
                        MarketItemDetailScreenState.Idle -> {}
                    }
                }
                AnimatedPane {
                    MarketItemDetailScreen(item = marketItem, itemDetail = itemDetail,
                        navigateToChat = {navigateToChat()},)

                }
            }




        },

        )


}





