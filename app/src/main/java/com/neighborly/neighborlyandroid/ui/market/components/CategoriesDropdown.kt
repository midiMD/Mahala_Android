package com.neighborly.neighborlyandroid.ui.market.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.ui.market.CategoryOption

@Composable
@Preview
fun MultiDropdownPreview() {
    val mockList = List(5) { _ ->
        CategoryOption(
            category = Category.ELECTRONICS,
            isActive = false
        )
    }
    val mockOnOptionToggled: (category: Category, value: Boolean) -> Unit = { category, value ->
        Log.d("logs", "Category : $category.text, Value: $value")
    }
    CategoriesDropdown(mockList, onOptionToggled = mockOnOptionToggled)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// every time a box is toggled -> onOptionToggled is triggered -> updates list in ViewModel
fun CategoriesDropdown(
    options: List<CategoryOption>,
    onOptionToggled: (category: Category, value:Boolean) -> Unit, /// Int = index of the option as passed to `options`, value:Boolean = new value of that option
    modifier:Modifier= Modifier
    ) {
    val textFieldState = rememberTextFieldState("Categories")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier,
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. A read-only text field has
            // the anchor type `PrimaryNotEditable`.
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            state = textFieldState,
            readOnly = true,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = { Text("Categories") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {

            for (option in options) {
                //use derivedStateOf to evaluate if it is checked
                //var checked by remember(derivedStateOf{ option.isActive })
                val checked  = option.isActive
                Log.i("testDropdown",option.category.toString() +": "+ checked)
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { newCheckedState ->
                                    //checked = newCheckedState
                                    onOptionToggled(option.category, newCheckedState)
                                },
                            )
                            Text(text = option.category.text)
                        }
                    },
                    onClick = {
                        onOptionToggled(option.category, !checked)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

