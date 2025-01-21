package com.neighborly.neighborlyandroid.ui.market.components


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.unit.dp

import com.neighborly.neighborlyandroid.domain.model.Category
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.tooling.preview.Preview

import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.ui.common.image.BrokenItemImage
import com.neighborly.neighborlyandroid.ui.common.image.ItemImage

@Composable
fun MarketItemsSection(modifier: Modifier=Modifier, itemsState: List<MarketItem>, onClickItem: (item: MarketItem) -> Unit){
    Surface(color = MaterialTheme.colorScheme.background,  ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),  // Set 2 items per row
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp),  // Optional spacing between rows
            horizontalArrangement = Arrangement.spacedBy(5.dp) // Optional spacing between items in the same row
        ) {

            items(items = itemsState, key = { item -> item.id }) { item ->
                MarketItemCard(item, onClick = {onClickItem(it)})
            }
        }


    }
}

val mockItems = List(10) { id ->
    MarketItem(
        id = id.toLong(),
        title = "Lawnmower",
        ownerName = "Dmitrie",
        dayCharge = 4.toDouble(),
        category = Category.ELECTRONICS,
        thumbnailUrl = "https://www.greenworkstools.co.uk/wp-content/uploads/2018/08/GWGD60LM46SP_01-scaled.jpg",
        distance = 0.1
    )
}
////@Preview
//@Composable
//fun PreviewItemCard(){
//    ItemCard(item = mockItems[0])
//}
@Composable
@Preview
fun PreviewItemsSection(){
    MarketItemsSection(itemsState = mockItems, onClickItem = {})
}
