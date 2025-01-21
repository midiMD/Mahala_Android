package com.neighborly.neighborlyandroid.ui.inventory.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.domain.model.InventoryItem


@Composable
fun InventoryItemsSection(modifier: Modifier=Modifier, items: List<InventoryItem>, onClickItem: (item: InventoryItem) -> Unit){
    Surface(color = MaterialTheme.colorScheme.background  ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),  // Set 2 items per row
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp),  // Optional spacing between rows
            horizontalArrangement = Arrangement.spacedBy(5.dp) // Optional spacing between items in the same row
        ) {

            items(items = items, key = { item -> item.id }) { item ->
                InventoryItemCard(item, onClick = {onClickItem(it)})
            }
        }


    }
}

//


val mockItems = List(10) { id ->
    InventoryItem(
        id = id.toLong(),
        title = "Lawnmower",
        dayCharge = 4.toDouble(),
        category = Category.ELECTRONICS,
        thumbnailUrl = null,

    )
}

@Composable
@Preview
fun PreviewItemsSection(){
    InventoryItemsSection(items = mockItems, onClickItem = {})
}

