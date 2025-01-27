package com.neighborly.neighborlyandroid.ui.inventory.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldDefaults
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.common.LoadingOverlay
import com.neighborly.neighborlyandroid.ui.inventory.components.InventoryItemsSection

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ViewInventoryScreen(modifier: Modifier,viewModel:ViewInventoryViewModel = viewModel(factory = ViewInventoryViewModel.Factory),
                ) {

    val items by viewModel.itemsState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>(
        scaffoldDirective = calculatePaneScaffoldDirective(currentWindowAdaptiveInfo()),
        adaptStrategies = ListDetailPaneScaffoldDefaults.adaptStrategies(),
    )
    val coroutineScope = rememberCoroutineScope()
    val itemDetail: InventoryItemDetail by viewModel.itemDetailState.collectAsStateWithLifecycle()
    val itemDetailScreenState: InventoryItemDetailScreenState by viewModel.detailScreenState.collectAsStateWithLifecycle()

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                InventoryListScreen(
                    items = items, uiState = uiState, snackbarHostState = snackbarHostState,
                    navigateToItemDetail = {item:InventoryItem ->
                        viewModel.getItemDetail(item.id)
                        coroutineScope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail, item
                            )
                        }
                    },
                    modifier = modifier,
                )
            }

        },
        detailPane = {
            val content = navigator.currentDestination?.contentKey
            if (content != null){
                val inventoryItem: InventoryItem =  content as InventoryItem
                AnimatedPane {
                    InventoryItemDetailScreen(
                        item = inventoryItem, detail = itemDetail,
                        detailScreenState= itemDetailScreenState,
                        modifier =modifier,
                        deleteItem = viewModel::deleteItem,
                        snackbarHostState = snackbarHostState,
                        navigateToListView = {
                            coroutineScope.launch {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.List
                                )
                            }
                        }
                    )

                }
            }




        },

        )

}

// Where inventory items are listed
@Composable
private fun InventoryListScreen(
    items:List<InventoryItem>,
    uiState: ViewInventoryScreenState,
    snackbarHostState: SnackbarHostState,
    navigateToItemDetail: (item: InventoryItem) -> Unit,
    modifier:Modifier = Modifier
){

    Column(modifier = modifier) {
        when (uiState){
            is ViewInventoryScreenState.Error -> {
                InventoryItemsSection(modifier = Modifier.background(color = Color.Cyan), items = items, onClickItem = {navigateToItemDetail(it)})
                LaunchedEffect(uiState) {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }

            ViewInventoryScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            ViewInventoryScreenState.Idle,
            ViewInventoryScreenState.Success -> {
                InventoryItemsSection(modifier = Modifier.background(color = MaterialTheme.colorScheme.background), items = items, onClickItem = {navigateToItemDetail(it)})
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun PreviewInventoryListScreen(){
    val items = List(10) { id->
    InventoryItem(
        id = 1L,
        title = "Mountain Bike",
        dayCharge = 15.99,
        category = null,
        thumbnailUrl = null
    )}
    val uiState = ViewInventoryScreenState.Success
    InventoryListScreen(
        items = items, uiState = uiState,
        snackbarHostState = SnackbarHostState(),
        navigateToItemDetail = {}
    )

}