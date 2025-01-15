package com.neighborly.neighborlyandroid.ui.inventory.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.SortBy
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.market.CategoryOption
import com.neighborly.neighborlyandroid.ui.market.MarketScreenState
import com.neighborly.neighborlyandroid.ui.market.components.MarketItemsSection
import com.neighborly.neighborlyandroid.ui.market.components.MarketTopBar

@Composable
fun ViewInventoryScreen(viewModel:ViewInventoryViewModel = viewModel(factory = ViewInventoryViewModel.Factory),
                ) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {

    }
}

// Where inventory items are listed
@Composable
private fun InventoryListScreen(
    itemsState:List<InventoryItem>,
    uiState: ViewInventoryScreenState,
    snackbarHostState: SnackbarHostState,
    navigateToItemDetail: (item: MarketItem) -> Unit,
    modifier:Modifier = Modifier
){

//    Column(modifier = modifier) {
//        when (uiState){
//            is ViewInventoryScreenState.Error -> {
//                ItemsSection(modifier = Modifier.background(color = Color.Cyan), itemsState = itemsState, onClickItem = {navigateToItemDetail(it)})
//                LaunchedEffect(uiState) {
//                    snackbarHostState.showSnackbar(uiState.message)
//                }
//            }
//
//            ViewInventoryScreenState.Loading -> {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//            ViewInventoryScreenState.Idle,
//            ViewInventoryScreenState.Success -> {
//                ItemsSection(modifier = Modifier.background(color = Color.Cyan), itemsState = itemsState, onClickItem = {navigateToItemDetail(it)})
//
//            }
//        }
//
//    }
}