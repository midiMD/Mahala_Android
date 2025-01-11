package com.neighborly.neighborlyandroid.ui

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.rememberNavController
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.ui.navigation.Screen
import kotlinx.coroutines.flow.debounce



@Composable
fun rememberMahalaAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    MahalaAppState(navController, context)
}
class MahalaAppState(
    val navController: NavHostController,
    private val context: Context
) {
    private val _snackbarHostState = SnackbarHostState()
    val snackbarHostState: SnackbarHostState get() = _snackbarHostState


    fun navigateToMarketItemDetails(marketItem: MarketItem, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(Screen.MarketItemDetails.createRoute(marketItem))
        }
    }
}
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

