package com.neighborly.neighborlyandroid.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Preview
@Composable
fun LoadingSpinner(modifier:Modifier = Modifier){
    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}
@Composable
@Preview
fun SuccessTick(){
    Box(modifier = Modifier.fillMaxSize()){
        Icon(imageVector = Icons.Filled.Check, contentDescription = "Registration success tick", tint = Color.Green, modifier = Modifier.size(70.dp).align(
            Alignment.Center))
    }
}