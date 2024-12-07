package com.neighborly.neighborlyandroid.market.ui


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.common.models.Category
import com.neighborly.neighborlyandroid.market.models.MarketItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.painter.BitmapPainter
import coil3.imageLoader
import coil3.util.DebugLogger
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import okhttp3.internal.http2.Http2Reader.Companion.logger

@Composable
fun MarketItemsSection(modifier: Modifier=Modifier, itemSectionState:MarketItemSectionState){
    Surface(color = Color.LightGray,  ) {
        if (itemSectionState.isLoading){
            Log.i("logs","MarketItemsSection loading")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
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
fun ItemCardDescription(item:MarketItem, modifier: Modifier){

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
fun ItemCard(item: MarketItem,modifier:Modifier = Modifier){
    OutlinedCard(
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
            val imageLoader = LocalContext.current.imageLoader.newBuilder()
                .logger(DebugLogger())
                .build()
            AsyncImage(
                model  = ImageRequest.Builder(LocalContext.current)
                    .data(item.thumbnailUrl.replace("127.0.0.1", "10.0.2.2")) // 127.0.0.1 refers to the emulator itself, 10.0.2.2 refers to the device that's running android studio
                    .crossfade(true)
                    .build(),
                contentDescription = item.toString(),
                error =painterResource(R.drawable.ic_broken_img),
                placeholder = painterResource(R.drawable.loading_image),
                contentScale = ContentScale.Fit,
                imageLoader = imageLoader,
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().weight(1f)
            )

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
//@Preview
@Composable
fun PreviewItemCard(){
    ItemCard(item = mockItems[0])

}
@Composable
@Preview
fun PreviewItemsSection(){
    MarketItemsSection(itemSectionState = MarketItemSectionState(items = mockItems))
}
//@Composable
//@Preview
////fun previewMarketItemsSection(){
////    MarketItemsSection(itemList = mockItems, isLoading = false)
////}