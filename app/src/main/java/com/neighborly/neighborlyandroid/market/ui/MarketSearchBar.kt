package com.neighborly.neighborlyandroid.market.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.common.models.Category
import com.neighborly.neighborlyandroid.common.models.SortBy


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
