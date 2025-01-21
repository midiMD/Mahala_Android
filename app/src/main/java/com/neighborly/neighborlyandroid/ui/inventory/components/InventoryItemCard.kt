package com.neighborly.neighborlyandroid.ui.inventory.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.ui.common.image.ListViewImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryItemCard(item: InventoryItem, onClick:(item: InventoryItem)->Unit, modifier: Modifier = Modifier){
    OutlinedCard(
        onClick = {onClick(item)} ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.padding(0.dp).size(100.dp,250.dp)

    ){
        Log.i("logs","InventoryItemsSection : item card: $item")
        Column (modifier = Modifier.fillMaxSize().padding(0.dp),
            verticalArrangement = Arrangement.SpaceBetween){
            ListViewImage(modifier = Modifier.align(Alignment.CenterHorizontally),thumbnailUri = item.thumbnailUrl)

            InventoryItemCardFooter(item, modifier = modifier.fillMaxHeight())


        }
    }

}
@Composable
@Preview
fun PreviewItemCard(){
    InventoryItemCard(
        mockItems[0],
        onClick = {}
    )
}