package com.neighborly.neighborlyandroid.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
internal fun ConfirmationDialog(title:String, text:String ="",
                                buttonTextColor: Color = MaterialTheme.colorScheme.primary,
                                hideDialog:()->Unit,
                                onClick:()->Unit){
    AlertDialog(
        onDismissRequest = hideDialog,
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            TextButton(
                onClick = {
                    hideDialog()
                    onClick()
                }
            ) {
                Text("Confirm", color = buttonTextColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = hideDialog
            ) {
                Text("Cancel")
            }
        }
    )
}