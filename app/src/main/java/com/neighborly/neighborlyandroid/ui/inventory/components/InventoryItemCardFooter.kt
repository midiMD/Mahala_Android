package com.neighborly.neighborlyandroid.ui.inventory.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.domain.model.InventoryItem

@Composable
fun InventoryItemCardFooter(item: InventoryItem, modifier: Modifier){

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp) // Add padding to the whole column
    ) {
        // Title
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium, // Dynamic font style from the theme
            color = MaterialTheme.colorScheme.primary, // Use primary color from the theme
            modifier = Modifier
                .fillMaxWidth(),
            //.padding(bottom = 1.dp), // Add space below the title
            textAlign = TextAlign.Center // Center the text
        )

        // Price
        Text(
            text = "£${item.dayCharge} per day",
            style = MaterialTheme.typography.bodySmall, // Dynamic subtitle style
            color = MaterialTheme.colorScheme.secondary, // Use secondary color from the theme
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp), // Add space above and below
            textAlign = TextAlign.Center // Center the text
        )
    }
}