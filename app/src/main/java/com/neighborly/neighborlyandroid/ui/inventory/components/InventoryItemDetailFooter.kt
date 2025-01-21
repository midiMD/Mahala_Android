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
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail

//The part below the image in Inventory Item Detail screen.
@Composable
fun InventoryItemDetailFooter(
    item: InventoryItem,
    detail: InventoryItemDetail?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp) // Add padding to the whole column
    ) {
        // Title
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleLarge, // Dynamic font style from the theme
            color = MaterialTheme.colorScheme.onBackground, // Use primary color from the theme
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), // Add space below the title
            textAlign = TextAlign.Center // Center the text
        )

        // Price
        Text(
            text = "£${item.dayCharge} per day",
            style = MaterialTheme.typography.bodyMedium, // Dynamic subtitle style
            color = MaterialTheme.colorScheme.onBackground, // Use secondary color from the theme
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Add space above and below
            textAlign = TextAlign.Center // Center the text
        )

        // Description
        if (detail?.description != null) {
            Text(
                text = detail.description,
                style = MaterialTheme.typography.bodyMedium, // Dynamic body style
                color = MaterialTheme.colorScheme.onBackground, // Use onBackground color for readability
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), // Add space above the description
                textAlign = TextAlign.Center // Center the text
            )
        }
    }
}