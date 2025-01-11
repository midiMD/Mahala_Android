package com.neighborly.neighborlyandroid.ui.market.components

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
import com.neighborly.neighborlyandroid.domain.model.SortBy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// every time a box is toggled -> onOptionToggled is triggered -> updates list in ViewModel
fun SortByDropdown(
    labelText: String,
    options: List<SortBy>,
    onOptionToggled: (sortBy: SortBy, value:Boolean) -> Unit, /// Int = index of the option as passed to `options`, value:Boolean = new value of that option
    modifier: Modifier = Modifier,
    activeOption: SortBy = SortBy.NOTHING
) {
    var expanded by remember { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState("Sort")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            state = textFieldState,
            readOnly = true,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = { Text("Sort") },
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

                var checked = false
                if (option == activeOption){
                    checked=true
                }


                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { newCheckedState ->
                                    onOptionToggled(option, newCheckedState)
                                },
                            )
                            Text(text = option.text)
                        }
                    },
                    onClick = {
                        onOptionToggled(option, !checked)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
