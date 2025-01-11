package com.neighborly.neighborlyandroid.ui.common.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.ui.theme.Purple80
@Composable
fun BrokenItemImage(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
){
    Image(
        painter = painterResource(id = R.drawable.ic_broken_img),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
    )
}
@Composable
fun ItemImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
//    if (LocalInspectionMode.current) {
//        Box(modifier = modifier.background(MaterialTheme.colorScheme.primary))
//        return
//    }

    var imagePainterState by remember {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }

    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = contentScale,
        onState = { state -> imagePainterState = state }
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (imagePainterState) {
            is AsyncImagePainter.State.Loading,
            is AsyncImagePainter.State.Error -> {
                BrokenItemImage()
            }
            else -> {
                Image(
                    painter = imageLoader,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = modifier,
                )
            }
        }


    }
}
