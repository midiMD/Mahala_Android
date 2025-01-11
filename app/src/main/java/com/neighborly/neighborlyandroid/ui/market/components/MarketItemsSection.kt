package com.neighborly.neighborlyandroid.ui.market.components


import android.util.Log
import androidx.compose.foundation.BorderStroke
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

import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.ui.common.image.BrokenItemImage
import com.neighborly.neighborlyandroid.ui.common.image.ItemImage

@Composable
fun MarketItemsSection(modifier: Modifier=Modifier, itemsState: List<MarketItem>, onClickItem: (item: MarketItem) -> Unit){
    Surface(color = Color.LightGray,  ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),  // Set 2 items per row
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp),  // Optional spacing between rows
            horizontalArrangement = Arrangement.spacedBy(5.dp) // Optional spacing between items in the same row
        ) {

            items(items = itemsState, key = { item -> item.id }) { item ->
                ItemCard(item, onClick = {onClickItem(it)})
            }
        }


    }
}

@Composable
fun ItemCardDescription(item: MarketItem, modifier: Modifier){

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

        Text(item.title) // item title
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),// Make sure the Row takes the full width
        horizontalArrangement = Arrangement.SpaceBetween // Place items at opposite ends
    ) {
        Text(item.ownerName)
        Text(String.format("%.1f", item.distance) + " miles")


    }
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text("£"+item.dayCharge+" per day")
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(item: MarketItem, onClick:(item:MarketItem)->Unit,modifier:Modifier = Modifier){
    OutlinedCard(
        onClick = {onClick(item)} ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.padding(1.dp).size(100.dp,250.dp)

    ){
        Log.i("logs","MarketItemsSection : item card: $item")
        Column (modifier = Modifier.fillMaxSize().padding(1.dp),
            verticalArrangement = Arrangement.SpaceBetween){
            //Surface(modifier = Modifier.padding(1.dp)){
            if (!item.thumbnailUrl.isNullOrEmpty()){
                Box(modifier = Modifier.align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center){
                    ItemImage(
                        item.thumbnailUrl.replace("127.0.0.1", "10.0.2.2"),
                        contentDescription = item.title,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Fit
                    )

                }
//                val imageLoader = LocalContext.current.imageLoader.newBuilder()
//                    .logger(DebugLogger())
//                    .build()
//                AsyncImage(
//                    model  = ImageRequest.Builder(LocalContext.current)
//                        .data(item.thumbnailUrl.replace("127.0.0.1", "10.0.2.2")) // 127.0.0.1 refers to the emulator itself, 10.0.2.2 refers to the device that's running android studio
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = item.toString(),
//                    error =painterResource(R.drawable.ic_broken_img),
//                    placeholder = painterResource(R.drawable.loading_image),
//                    contentScale = ContentScale.Fit,
//                    imageLoader = imageLoader,
//                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().weight(1f)
//                )
            }else{
                BrokenItemImage()
            }

            ItemCardDescription(item, modifier = modifier.size(100.dp, 50.dp))


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
//@Composable
//@Preview
//fun PreviewItemsSection(){
//    MarketItemsSection(itemsState = mockItems)
//}
