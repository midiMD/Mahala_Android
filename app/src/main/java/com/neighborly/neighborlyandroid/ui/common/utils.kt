package com.neighborly.neighborlyandroid.ui.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

suspend fun showSnackbar(snackbarHostState:SnackbarHostState, message: String) {
    snackbarHostState.showSnackbar(
        message = message,
        //actionLabel = actionLabel,
        duration = SnackbarDuration.Short
    )
}