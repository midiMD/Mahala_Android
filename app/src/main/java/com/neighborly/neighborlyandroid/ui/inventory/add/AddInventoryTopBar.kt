package com.neighborly.neighborlyandroid.ui.inventory.add

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddInventoryTopBar(
    showDiscardDialog: () -> Unit,
    showSubmitDialog: () -> Unit,
    isSubmitEnabled: Boolean
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Add",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            TextButton(
                onClick = showDiscardDialog,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Discard")
            }
        },
        actions = {
            Button(
                onClick = showSubmitDialog,
                enabled = isSubmitEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Submit")
            }
        }
    )
}
