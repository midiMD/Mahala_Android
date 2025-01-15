package com.neighborly.neighborlyandroid.ui.market.item_detail

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
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.ui.common.image.BrokenItemImage
import com.neighborly.neighborlyandroid.ui.common.image.ItemImage
import com.neighborly.neighborlyandroid.ui.market.components.ItemCardDescription


@Composable
fun MarketItemDetailScreen(modifier: Modifier = Modifier,
                           item: MarketItem,
                           itemDetail:MarketItemDetail?,
                           navigateToChat:()->Unit) {

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
        }else{
            BrokenItemImage()
        }

        MarketItemDetailDescription(item,detail = itemDetail, modifier = modifier.size(100.dp, 150.dp))
        Button(onClick = { navigateToChat() }) {
            Text("Chat", fontSize = 16.sp)
        }
    }


}
@Composable
fun MarketItemDetailDescription(item: MarketItem, detail:MarketItemDetail?, modifier: Modifier){

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
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        if (detail != null) {
            Text(detail.description ?: "")
        }

    }

}


