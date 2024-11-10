package com.neighborly.neighborlyandroid.market.ui

import android.text.Layout.Alignment
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.neighborly.neighborlyandroid.common.models.Category
import com.neighborly.neighborlyandroid.market.models.MarketItem

@Composable
fun MarketItemsSection(modifier: Modifier=Modifier, itemSectionState:MarketItemSectionState){
    Surface {
        if (itemSectionState.isLoading){
            Log.i("logs","MarketItemsSection loading")
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        else{
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),  // Set 2 items per row
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp),  // Optional spacing between rows
                horizontalArrangement = Arrangement.spacedBy(5.dp) // Optional spacing between items in the same row
            ) {

                items(items = itemSectionState.items, key = { item -> item.id }) { item ->
                    ItemCard(item)
                }
            }
        }

    }
}

@Composable
fun ItemCard(item: MarketItem,modifier:Modifier = Modifier){
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.padding(2.dp)
    ){
        Column (modifier = Modifier.fillMaxWidth().padding(2.dp)){
            Surface(modifier = Modifier.padding(2.dp)){
                Image(
                    painter = rememberAsyncImagePainter(item.thumbnailUrl),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp)
                )
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

                Text(item.title) // item title
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),// Make sure the Row takes the full width
                horizontalArrangement = Arrangement.SpaceBetween // Place items at opposite ends
            ) {
                Text(item.ownerName)
                Text(item.distance.toString() + " miles")

            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("£"+item.dayCharge+" per day")
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
@Preview
@Composable
fun previewItemCard(){
    ItemCard(item = mockItems[0])

}
//@Composable
//@Preview
////fun previewMarketItemsSection(){
////    MarketItemsSection(itemList = mockItems, isLoading = false)
////}