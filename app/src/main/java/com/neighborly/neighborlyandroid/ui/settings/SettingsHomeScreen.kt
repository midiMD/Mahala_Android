package com.neighborly.neighborlyandroid.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState
import com.neighborly.neighborlyandroid.ui.common.ConfirmationDialog
import com.neighborly.neighborlyandroid.ui.common.LoadingOverlay
import com.neighborly.neighborlyandroid.ui.common.SuccessOverlay
import com.neighborly.neighborlyandroid.ui.common.showSnackbar
import com.neighborly.neighborlyandroid.ui.settings.components.PasswordChangeSection
import com.neighborly.neighborlyandroid.ui.settings.components.SettingsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    navigateToLogin:()->Unit

) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }

    // Handle snackbar messages
    snackbarMessage?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message = message)
            snackbarMessage = null // Reset after showing
        }
    }
    SettingsHomeScreen(modifier = modifier,
        uiState = uiState,
        onLogOut = {
            viewModel.logout()
        },
        navigateToLogin = navigateToLogin,
        showSnackbarMessage= {snackbarMessage = it},
        changePassword = viewModel::changePassword
        )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsHomeScreen(modifier: Modifier = Modifier,
                               navigateToLogin: () -> Unit,
                               uiState: SettingsScreenState,
                               showSnackbarMessage : (String)->Unit,
                               onLogOut:()->Unit,
                               changePassword:(oldPassword:String,newPassword:String)->Unit
){

    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var showPasswordChange by rememberSaveable { mutableStateOf(false) }
    var showEmailChange by rememberSaveable { mutableStateOf(false) }

    when(uiState){
        is SettingsScreenState.Error -> {
            showSnackbarMessage(uiState.message)
        }
        SettingsScreenState.Idle -> {}
        SettingsScreenState.Loading -> {
            LoadingOverlay()
        }
        SettingsScreenState.Success -> {
            SuccessOverlay()
        }

        SettingsScreenState.Logout -> {
            navigateToLogin()
        }
    }

    if (showPasswordChange){
        PasswordChangeSection(
            onConfirm = { oldPassword, newPassword ->
                changePassword(oldPassword, newPassword)
            },
            onBack = {showPasswordChange=false},
            showSnackbarMessage = showSnackbarMessage
        )
    }else if (showEmailChange){

    }else{
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Settings") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                // Change Email
                SettingsItem(
                    icon = Icons.Default.Email,
                    text = "Change Email",
                    onClick = { showEmailChange=true }
                )

                // Change Password
                SettingsItem(
                    icon = Icons.Default.Lock,
                    text = "Change Password",
                    onClick = { showPasswordChange = true }
                )

                // Log Out
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    text = "Log Out",
                    onClick = { showLogoutDialog = true }
                )
            }
        }
        if (showLogoutDialog) {
            ConfirmationDialog(
                title = "Confirm Logout",
                text = "Are you sure you want to Logout?",
                buttonTextColor = MaterialTheme.colorScheme.error,
                hideDialog = {showLogoutDialog=false},
                onClick = {onLogOut()}
            )
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun PreviewHomeScreen(){
//    SettingsHomeScreen(modifier = Modifier, showSnackbarMessage = TODO(), changePassword = TODO(),
//        onLogOut = {})
//}