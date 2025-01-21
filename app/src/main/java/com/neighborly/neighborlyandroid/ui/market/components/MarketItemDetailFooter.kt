package com.neighborly.neighborlyandroid.ui.market.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail

// The part under the thumbnail image that displays the details of the market item
@Composable
fun MarketItemDetailFooter(item: MarketItem, detail: MarketItemDetail?, modifier: Modifier){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp) // Add padding to the whole column
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
        // Owner name and distance
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(vertical = 0.dp),// Make sure the Row takes the full width
            horizontalArrangement = Arrangement.SpaceBetween // Place items at opposite ends
        ) {
            Text(text = item.ownerName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                )
            Text(text = String.format("%.1f", item.distance) + " miles",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )

        }
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
