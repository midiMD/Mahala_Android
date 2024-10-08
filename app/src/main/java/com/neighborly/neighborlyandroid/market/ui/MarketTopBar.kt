package com.neighborly.neighborlyandroid.market.ui

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp



@Composable
fun MarketTopBar(modifier: Modifier=Modifier, onSearch:(String)->Unit, onToggleCategories:(Boolean)-> Unit, onToggleSortBy:(Boolean)->Unit){
    Column {
        MarketSearchBar(modifier = modifier, onSearchButtonPress = onSearch)
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
            SortByButton(onClick = onToggleSortBy)
            CategoriesButton(onClick = onToggleCategories)
        }
    }


}
@Composable
fun MarketSearchBar( modifier:Modifier,onSearchButtonPress:(String)->Unit){
    // search bar with search icon button
    // need to store state so that the UI is recomposed on change of input
    // by keyword allows to skip the .value when interacting with state
    // stateful composable, keep track of searchQuery
    var searchQuery:String = rememberSaveable() {""}
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

@Composable
fun CategoriesButton(onClick:(Boolean)->Unit){
    var toggle:Boolean = rememberSaveable() { false }
    Row(horizontalArrangement = Arrangement.End,modifier = Modifier.fillMaxWidth()){
        OutlinedButton(onClick = { onClick(!toggle) }) {
            Text("Categories")
        }
    }
}

@Composable
fun SortByButton(onClick:(Boolean)->Unit) {
    var toggle:Boolean = rememberSaveable() { false }
    OutlinedButton(onClick = {onClick(!toggle)}) {
        Text("Sort By")
    }
}