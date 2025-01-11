package com.neighborly.neighborlyandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.neighborly.neighborlyandroid.ui.theme.NeighborlyAndroidTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NeighborlyAndroidTheme {
                MahalaApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NeighborlyAndroidTheme {
        MahalaApp()
    }
}