package com.neighborly.neighborlyandroid.ui.market.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.domain.model.SortBy
import com.neighborly.neighborlyandroid.ui.market.CategoryOption


@Composable
fun MarketTopBar(modifier: Modifier=Modifier, onSearch:(String)->Unit,
                 categoryState:List<CategoryOption>,
                 onToggleCategories:(category: Category, value:Boolean)-> Unit,
                 onToggleSortByOptions:(option: SortBy, value:Boolean)->Unit,
                 activeSortBy: SortBy, sortByOptions:List<SortBy>){
    var isVisibleCategoryAndSortByBar by remember { mutableStateOf(true) }
    Column {
        MarketSearchBar(modifier = modifier, onSearchButtonPress = onSearch,
            toggleCategoryAndSortByVisibility = {value->isVisibleCategoryAndSortByBar = value})
        if (isVisibleCategoryAndSortByBar){
            CategoriesAndSortByBar(modifier = modifier, categoryState=categoryState,
                onToggleCategories = onToggleCategories,
                onToggleSortByOptions = onToggleSortByOptions,
                activeSortBy = activeSortBy,
                sortByOptions = sortByOptions)
        }



    }


}
@Composable
fun CategoriesAndSortByBar(modifier: Modifier, categoryState:List<CategoryOption>, onToggleCategories: (category: Category, value: Boolean) -> Unit, onToggleSortByOptions: (option: SortBy, value: Boolean) -> Unit, activeSortBy: SortBy, sortByOptions: List<SortBy>){
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
        CategoriesDropdown(
            options = categoryState, onOptionToggled = onToggleCategories, modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp / 2)  // Half screen width
        )
        SortByDropdown(
            onOptionToggled = onToggleSortByOptions,
            activeOption = activeSortBy,
            labelText = "Sort By",
            options = sortByOptions
        )
    }
}

