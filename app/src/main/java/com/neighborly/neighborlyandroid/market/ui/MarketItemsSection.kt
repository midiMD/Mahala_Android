package com.neighborly.neighborlyandroid.market.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.neighborly.neighborlyandroid.market.models.Item

@Composable
fun MarketItemsSection(modifier: Modifier=Modifier, itemList:List<Item>){
    Surface {
        LazyColumn(
            modifier = modifier
        ) {
            items(items = itemList,key = {item->item.id}){  // by providing a key, Compose won't recompose when the order of items has changed, only when an item has been added or removed
                item-> ItemCard(item)
            }
        }
    }
}

@Composable
fun ItemCard(item: Item){

}