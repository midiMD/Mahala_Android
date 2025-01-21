package com.neighborly.neighborlyandroid.ui.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.domain.model.MarketItemDetail
import com.neighborly.neighborlyandroid.ui.common.image.DetailScreenMainImage
import com.neighborly.neighborlyandroid.ui.market.components.MarketItemDetailFooter


@Composable
fun MarketItemDetailScreen(modifier: Modifier = Modifier,
                           item: MarketItem,
                           detail:MarketItemDetail?,
                           navigateToChat:()->Unit) {

    Column (modifier = Modifier.fillMaxSize().padding(1.dp)){
        DetailScreenMainImage(thumbnailUrl = item.thumbnailUrl, modifier = Modifier.align(Alignment.CenterHorizontally))
        MarketItemDetailFooter(item,detail = detail, modifier = modifier.height(200.dp))
        ChatButton(modifier = Modifier.align(Alignment.CenterHorizontally),navigateToChat = navigateToChat)
    }

}

@Composable
private fun ChatButton(modifier:Modifier = Modifier,navigateToChat:()->Unit){
    Button(modifier = modifier, onClick = { navigateToChat()},
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, // Background color
            contentColor = MaterialTheme.colorScheme.onPrimary
        )) {
        Text("Chat", style=MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMarketItemDetailScreen(){
    val mockItem = MarketItem(
        id = 2,
        title = "Electric Lawnmower",
        ownerName = "Lady Day",
        dayCharge = 4.0,
        category = null,
        thumbnailUrl = null,
        distance = 0.2
    )
    val mockDetail = MarketItemDetail(description = "Powerful electric lawnmower for mowing electric lawns")
    MarketItemDetailScreen(item = mockItem,
        detail = mockDetail,
        navigateToChat = {})

}