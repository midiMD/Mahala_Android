package com.neighborly.neighborlyandroid.ui.market.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun CheckboxItem(text:String, onToggle:(Boolean)->Unit) {
    var checked by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(text, style = MaterialTheme.typography.bodyLarge)
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onToggle(it) }
        )
    }

}