package com.neighborly.neighborlyandroid.ui.market.components

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController


//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//fun MarketSearchBarPreview() {
//    MarketSearchBar(modifier = Modifier, onSearchButtonPress = {search -> Log.i("logs",search)})
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketSearchBar(modifier:Modifier,onSearchButtonPress:(String)->Unit,
                    toggleCategoryAndSortByVisibility:(Boolean)->Unit) {
    val textFieldState = rememberTextFieldState()
    var expanded = false
    val keyboardController = LocalSoftwareKeyboardController.current
    //Box(Modifier.fillMaxSize().semantics { isTraversalGroup = false }) {
        SearchBar(
//            modifier = Modifier.align(Alignment.TopCenter).semantics { traversalIndex = 0f },

            inputField = {
                SearchBarDefaults.InputField(
                    state = textFieldState,
                    onSearch = { expanded = false
                                keyboardController?.hide()
                                onSearchButtonPress(textFieldState.text.toString())
                    },

                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search Market") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    //trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ){}

}
