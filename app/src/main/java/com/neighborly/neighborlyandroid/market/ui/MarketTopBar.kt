package com.neighborly.neighborlyandroid.market.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.common.models.Category
import com.neighborly.neighborlyandroid.common.models.SortBy


@Composable
fun MarketTopBar(modifier: Modifier=Modifier, onSearch:(String)->Unit, categoryState:List<CategoryOption>, onToggleCategories:(category:Category, value:Boolean)-> Unit, onToggleSortByOptions:(option: SortBy, value:Boolean)->Unit,activeSortBy: SortBy,sortByOptions:List<SortBy>){
    Column {
        MarketSearchBar(modifier = modifier, onSearchButtonPress = onSearch)
//        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
//            //CategoriesDropdown(categoryOptions = categoryState, onToggleCategories = onToggleCategories)
//            //SortByDropdown(onOptionToggled = onToggleSortByOptions,activeOption = activeSortBy, labelText = "Sort By", options = sortByOptions)
//        }
        CategoriesDropdown(options = categoryState, onOptionToggled = onToggleCategories,modifier = Modifier
            .width(LocalConfiguration.current.screenWidthDp.dp / 2)  // Half screen width
            )

    }


}
@Composable
fun MarketSearchBar( modifier:Modifier,onSearchButtonPress:(String)->Unit){
    // search bar with search icon button
    // need to store state so that the UI is recomposed on change of input
    // by keyword allows to skip the .value when interacting with state
    // stateful composable, keep track of searchQuery
    var searchQuery by rememberSaveable { mutableStateOf("") }
    Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Start) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(0.82f)
        )
        FilledIconButton(onClick = { onSearchButtonPress(searchQuery) },modifier= Modifier.padding(4.dp,0.dp).fillMaxWidth()) {
            Icon(Icons.Filled.Search, contentDescription = "Search")
        }

    }
}


//@Composable
//fun CategoriesDropdown(categoryOptions: List<CategoryOption>, onToggleCategories: (category: Category, toggle: Boolean) -> Unit){
//    var toggle:Boolean = rememberSaveable() { false }
//    Row(horizontalArrangement = Arrangement.End,modifier = Modifier.fillMaxWidth()){
//        OutlinedButton(onClick = { onClick(!toggle) }) {
//            Text("Categories")
//        }
//    }
//}

//@Composable
//fun SortByDropdown(sortByOptions: List<SortBy>, onToggleSortByOptions:(Boolean)->Unit) {
//    var toggle:Boolean = rememberSaveable() { false }
//    OutlinedButton(onClick = {onClick(!toggle)}) {
//        Text("Sort By")
//    }
//}