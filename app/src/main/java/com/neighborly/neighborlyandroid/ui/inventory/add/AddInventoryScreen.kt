package com.neighborly.neighborlyandroid.ui.inventory.add

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.navigation.Screen

@Composable
fun AddInventoryScreen(onNavigateToInventoryHome:()->Unit,
                        viewModel:AddInventoryViewModel = viewModel(factory = AddInventoryViewModel.Factory),
                ) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {

    }
}
//@Preview
//@Composable
//fun AddInventoryScreenPreview(){
//    AddInventoryScreen(onNavigateToScreen ={screen-> Log.d("logs", "Navigating to screen: " + screen.route)} )
//}
