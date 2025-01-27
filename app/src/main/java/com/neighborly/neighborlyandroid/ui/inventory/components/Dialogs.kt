package com.neighborly.neighborlyandroid.ui.inventory.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.neighborly.neighborlyandroid.ui.common.ConfirmationDialog

@Composable
internal fun DiscardDialog(hideDialog:()->Unit,
                           onClick:()->Unit){
    ConfirmationDialog(
        title = "Discard?",
        text = "All data will be lost.",
        buttonTextColor = MaterialTheme.colorScheme.error,
        hideDialog = hideDialog,
        onClick = onClick
    )
}
@Composable
internal fun SubmitDialog(hideDialog:()->Unit,
                          onClick:()->Unit){
    ConfirmationDialog(
        title = "Submit?",
        text = "",
        buttonTextColor = MaterialTheme.colorScheme.primary,
        hideDialog =hideDialog,
        onClick = onClick
    )
}
@Composable
internal fun DeleteItemDialog(hideDialog:()->Unit,
                              onClick:()->Unit){
    ConfirmationDialog(
        title = "Delete?",
        text = "Delete item",
        buttonTextColor = MaterialTheme.colorScheme.primary,
        hideDialog =hideDialog,
        onClick = onClick
    )

}