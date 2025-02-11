package com.neighborly.neighborlyandroid.ui.settings.components

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.ui.common.ConfirmationDialog
import com.neighborly.neighborlyandroid.ui.theme.NeighborlyAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordChangeSection(onConfirm:(oldPassword:String,newPassword:String)->Unit,
                          onBack:()->Unit,
                          showSnackbarMessage:(String)->Unit) {
    var oldPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var showConfirmation by rememberSaveable { mutableStateOf(false) }
    BackHandler(enabled = true) {
        onBack()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Change Password") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                label = { Text("Current Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                supportingText = {
                    if (newPassword.length < 8) {
                        Text("Password must be at least 8 characters")
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showConfirmation = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = oldPassword.isNotEmpty() && newPassword.isNotEmpty()
            ) {
                Text("Change Password")
            }
        }
    }

    if (showConfirmation) {
        ConfirmationDialog(
            title = "Confirm Password Change",
            text = "Are you sure?",
            buttonTextColor = MaterialTheme.colorScheme.primary,
            hideDialog = { showConfirmation = false },
            onClick = {onConfirm(oldPassword,newPassword)},
        )
    }
}

@Preview
@Composable
fun PreviewSection(){


    PasswordChangeSection(
        onConfirm = { a, b -> Log.i("logs","pressend confirm")},
        onBack = {},
        showSnackbarMessage = TODO()
    )


}