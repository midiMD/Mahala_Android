package com.neighborly.neighborlyandroid.ui.inventory

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.R
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.common.LoadingSpinner
import com.neighborly.neighborlyandroid.ui.common.SuccessTick
import com.neighborly.neighborlyandroid.ui.common.showSnackbar
import com.neighborly.neighborlyandroid.ui.login.components.AccountQueryComponent
import com.neighborly.neighborlyandroid.ui.login.components.HeadingTextComponent
import com.neighborly.neighborlyandroid.ui.login.components.MyTextFieldComponent
import com.neighborly.neighborlyandroid.ui.login.components.PasswordTextFieldComponent
import com.neighborly.neighborlyandroid.ui.navigation.Screen
import com.neighborly.neighborlyandroid.ui.theme.AccentColor
import com.neighborly.neighborlyandroid.ui.theme.GrayColor
import com.neighborly.neighborlyandroid.ui.theme.Secondary

@Composable
fun InventoryScreen(onNavigateToScreen:(screen:Screen)->Unit,
//                viewModel:InventoryViewModel = viewModel(factory = InventoryViewModel.Factory),
                ) {

    //val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
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
                Button(onClick = { onNavigateToScreen(Screen.ViewInventory) }) {
                    Text("View Items", fontSize = 16.sp)
                }
                Button(onClick = { onNavigateToScreen(Screen.AddInventory) }) {
                    Text("Add Item", fontSize = 16.sp)
                }
            }
        }
    }
}
@Preview
@Composable
fun InventoryScreenPreview(){
    InventoryScreen(onNavigateToScreen ={screen-> Log.d("logs", "Navigating to screen: " + screen.route)} )
}
