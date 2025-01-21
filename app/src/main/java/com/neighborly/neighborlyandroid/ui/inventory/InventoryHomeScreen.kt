package com.neighborly.neighborlyandroid.ui.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InventoryScreen(onNavigateToInventoryView:()->Unit,onNavigateToInventoryAdd:()->Unit
                ) {

    //val uiState by viewModel.uiState.collectAsState()
    //val snackbarHostState = LocalSnackbarHostState.current
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Column to arrange the buttons vertically
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp), // Spacing between buttons
                horizontalAlignment = Alignment.CenterHorizontally // Align buttons horizontally
            ) {
                Button(onClick = onNavigateToInventoryView) {
                    Text("View Items", fontSize = 16.sp)
                }
                Button(onClick = onNavigateToInventoryAdd) {
                    Text("Add Item", fontSize = 16.sp)
                }
            }
        }
    }
}
//@Preview
//@Composable
//fun InventoryScreenPreview(){
//    InventoryScreen(onNavigateToScreen ={screen-> Log.d("logs", "Navigating to screen: " + screen.route)} )
//}
