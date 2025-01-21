package com.neighborly.neighborlyandroid.ui.common.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.dp
@Composable
fun DetailScreenMainImage(thumbnailUrl: String?,modifier: Modifier){
    Box(modifier = modifier.fillMaxWidth().fillMaxHeight(0.5f),
        contentAlignment = Alignment.Center) {
        if (!thumbnailUrl.isNullOrEmpty()) {

            ItemImage(
                thumbnailUrl.replace("127.0.0.1", "10.0.2.2"),
                contentDescription = "Item thumbnail",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        } else {
            BrokenItemImage()
        }
    }
}