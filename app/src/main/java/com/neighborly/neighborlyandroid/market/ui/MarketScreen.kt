package com.neighborly.neighborlyandroid.market.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.market.models.Item

// ! viewModel lifecycle will be that of MarketActivity > lifecycle of MarketScreen composable
@Composable
fun MarketScreen(modifier: Modifier = Modifier,
                 marketViewModel:MarketViewModel = viewModel()) {
    Column(modifier = modifier) {
        MarketTopBar(modifier = modifier,
            onSearch = {query->marketViewModel.onSearchButtonPress(query)},
            onToggleSortBy = {value->marketViewModel.toggleSortBy(value)},
            onToggleCategories = {value->marketViewModel.toggleCategories(value)} )
        val itemsList = remember {  emptyList<Item>().toMutableStateList() } // Where we request from server. will survive recomps
        MarketItemsSection(modifier = modifier, itemsList)
    }


}


