package com.neighborly.neighborlyandroid.ui.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun UnverifiedAddressScreen(backToLogin:()->Unit) {
    BackHandler {
        backToLogin()
    }
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center){
        Text(text = "Your address is being verified. Come back later or email us at contact@mahala.us", style = MaterialTheme.typography.bodyMedium)

    }
}

//@Composable
//@Preview
//fun Preview(){
//    UnverifiedAddressScreen()
//}