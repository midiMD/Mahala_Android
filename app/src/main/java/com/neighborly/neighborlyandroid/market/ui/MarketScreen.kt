package com.neighborly.neighborlyandroid.market.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.common.models.SortBy
import com.neighborly.neighborlyandroid.market.models.MarketItem

// ! viewModel lifecycle will be that of MarketActivity > lifecycle of MarketScreen composable
// View model
@Composable
fun MarketScreen(modifier: Modifier = Modifier,
                 marketViewModel:MarketViewModel = viewModel()) {
    val categoryState by marketViewModel.categoryState.collectAsState()
    Log.i("testDropdown", "Category state in MarketScreen"+categoryState.toString())
    val activeSortBy by marketViewModel.activeSortBy.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by marketViewModel.uiState.collectAsState()
    val itemSectionUiState by marketViewModel.itemSectionUiState.collectAsState()
    Column(modifier = modifier) {
        MarketTopBar(modifier = modifier,
            onSearch = {query->marketViewModel.onSearchButtonPress(query)},
            onToggleSortByOptions = { option: SortBy, value:Boolean -> marketViewModel.toggleSortByOption(option, value)},
            sortByOptions = SortBy.entries,
            activeSortBy = activeSortBy,
            categoryState = categoryState,
            onToggleCategories = {category, value -> marketViewModel.toggleCategory(category,value)})

       MarketItemsSection(modifier = modifier, itemSectionState = itemSectionUiState) }
    }
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//@Preview
//fun MarketplaceScreen() {
//    Scaffold(
//        topBar = {
//            // Main TopAppBar with basic navigation
//            TopAppBar(
//                title = { Text("Marketplace") },
//                actions = {
//
//                    IconButton(onClick = { /* Menu action */ }) {
//                        Icon(Icons.Default.Menu, "Menu")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//        ) {
//            // Search and Filter Section
//            SearchAndFilterSection()
//
//            // Rest of the content (product grid, etc.)
//            //ProductGrid()
//        }
//    }
//}
//@Composable
//fun SearchAndFilterSection() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    ) {
//        // Search Bar
//        OutlinedTextField(
//            value = "",
//            onValueChange = { },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            placeholder = { Text("Search products...") },
//            trailingIcon = {
//                IconButton(onClick = { /* Search action */ }) {
//                    Icon(Icons.Default.Search, "Search")
//                }
//            }
//        )
//
//        // Categories and Sort Row
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Categories Dropdown
//            ExposedDropdownMenuBox(
//                expanded = false,
//                onExpandedChange = { }
//            ) {
//                OutlinedTextField(
//                    value = "All Categories",
//                    onValueChange = { },
//                    readOnly = true,
//                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, "Categories") }
//                )
//            }
//
//            // Sort By Dropdown
//            ExposedDropdownMenuBox(
//                expanded = false,
//                onExpandedChange = { }
//            ) {
//                OutlinedTextField(
//                    value = "Sort By",
//                    onValueChange = { },
//                    readOnly = true,
//                    trailingIcon = { Icon(Icons.Default.Sort, "Sort") }
//                )
//            }
//        }
//    }
//}



