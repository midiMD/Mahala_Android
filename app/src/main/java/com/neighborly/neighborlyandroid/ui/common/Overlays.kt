package com.neighborly.neighborlyandroid.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Screen overlays for loading, success etc

@Composable
fun LoadingOverlay() {
    // place this inside the main screen scaffold body

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
            .clickable(enabled = false, onClick = {}), // Block clicks while loading
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + expandIn(),
            exit = fadeOut() + shrinkOut()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(0.3f),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 6.dp
            )
        }
    }
}
@Composable
fun SuccessOverlay() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
            .clickable(enabled = false, onClick = {}), // Block clicks while loading
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + expandIn(),
            exit = fadeOut() + shrinkOut()
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Success",
                modifier = Modifier.fillMaxSize(0.3f),
                tint = Color.Green,
            )
        }
    }
}
