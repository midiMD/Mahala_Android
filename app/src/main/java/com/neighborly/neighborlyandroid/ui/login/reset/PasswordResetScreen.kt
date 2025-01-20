package com.neighborly.neighborlyandroid.ui.login.reset

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.common.LoadingSpinner
import com.neighborly.neighborlyandroid.ui.common.SuccessTick
import com.neighborly.neighborlyandroid.ui.login.components.HeadingTextComponent

sealed class ResetScreenState{
    data object Idle : ResetScreenState()
    data object Success: ResetScreenState()
    data object Loading: ResetScreenState()
    data class Error(val message:String):ResetScreenState()


}
@Composable
internal fun PasswordResetScreen(
    uiState:ResetScreenState,
    toggleIdleState:()->Unit,
    snackbarHostState: SnackbarHostState,
    onClickReset:(email:String)->Unit
){
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            HeadingTextComponent(value = "Reset password")
        }
        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onClickReset(email) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset")
        }
        when(uiState){
            is ResetScreenState.Error -> {

                LaunchedEffect(uiState) {
                    snackbarHostState.showSnackbar(uiState.message)
                }
                //toggleIdleState()
            }
            ResetScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    LoadingSpinner(modifier = Modifier.align(Alignment.Center))
                }
            }
            ResetScreenState.Success -> {
                Box(
                    modifier = Modifier
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "Success!! If the email exists in our database, you will receive a temporary password",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            ResetScreenState.Idle ->{}
        }
    }
}

@Composable
fun PasswordResetScreen(
    viewModel: PasswordResetViewModel = viewModel(factory = PasswordResetViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    PasswordResetScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onClickReset = viewModel::onClickReset,
        toggleIdleState = viewModel::toggleIdle
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordResetScreenPreview() {
    val uiState:ResetScreenState  = ResetScreenState.Success
    val snackbarHostState = SnackbarHostState()

    PasswordResetScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onClickReset = {email-> Log.i("logs","Clicked Reset in Password reset")},
        toggleIdleState = {}
    )
    
}
