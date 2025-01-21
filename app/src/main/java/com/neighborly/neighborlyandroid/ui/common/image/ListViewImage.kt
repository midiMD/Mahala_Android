package com.neighborly.neighborlyandroid.ui.common.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
fun ListViewImage(modifier:Modifier = Modifier,thumbnailUri:String?){
    Box(modifier = modifier.fillMaxWidth().fillMaxHeight(0.7f),
        contentAlignment = Alignment.Center) {
        if (!thumbnailUri.isNullOrEmpty()) {

            ItemImage(
                thumbnailUri.replace("127.0.0.1", "10.0.2.2"),
                contentDescription = "Item Thumbnail",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

        } else {
            BrokenItemImage()
        }
    }
}