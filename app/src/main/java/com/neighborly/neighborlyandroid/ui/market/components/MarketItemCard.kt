package com.neighborly.neighborlyandroid.ui.market.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.ui.common.image.BrokenItemImage
import com.neighborly.neighborlyandroid.ui.common.image.ItemImage
import com.neighborly.neighborlyandroid.ui.common.image.ListViewImage


@Composable
fun MarketItemCardFooter(item: MarketItem, modifier: Modifier){

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp) // Add padding to the whole column
    ) {
        // Title
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleSmall, // Dynamic font style from the theme
            color = MaterialTheme.colorScheme.primary, // Use primary color from the theme
            modifier = Modifier
                .fillMaxWidth(),
            //.padding(bottom = 1.dp), // Add space below the title
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
            style = MaterialTheme.typography.bodySmall, // Dynamic subtitle style
            color = MaterialTheme.colorScheme.secondary, // Use secondary color from the theme
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp), // Add space above and below
            textAlign = TextAlign.Center // Center the text
        )
    }
}
@Composable
fun MarketItemCard(item: MarketItem, onClick:(item: MarketItem)->Unit, modifier: Modifier = Modifier){
    OutlinedCard(
        onClick = {onClick(item)} ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier.padding(1.dp).size(100.dp,250.dp)

    ){
        Log.i("logs","MarketItemsSection : item card: $item")
        Column (modifier = Modifier.fillMaxSize().padding(1.dp),
            verticalArrangement = Arrangement.SpaceBetween){
            ListViewImage(modifier = Modifier.align(Alignment.CenterHorizontally),thumbnailUri = item.thumbnailUrl)
            MarketItemCardFooter(item, modifier = Modifier.fillMaxHeight())
        }
    }

}