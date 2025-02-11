package com.neighborly.neighborlyandroid.ui

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.ui.navigation.ChatBaseRoute
import com.neighborly.neighborlyandroid.ui.navigation.InventoryBaseRoute
import com.neighborly.neighborlyandroid.ui.navigation.MarketRoute
import com.neighborly.neighborlyandroid.ui.navigation.TopLevelDestination
import com.neighborly.neighborlyandroid.ui.navigation.navigateToChatHome
import com.neighborly.neighborlyandroid.ui.navigation.navigateToInventoryHome
import com.neighborly.neighborlyandroid.ui.navigation.navigateToMarket
import com.neighborly.neighborlyandroid.ui.navigation.navigateToSettingsHome
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
    private val previousDestination = mutableStateOf<NavDestination?>(null)



    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return topLevelDestinations.firstOrNull { topLevelDestination ->
                currentDestination?.hierarchy?.any {
                    it.hasRoute(topLevelDestination.baseRoute)
                } ?: false
           //     currentDestination?.hasRoute(route = topLevelDestination.baseRoute) == true
            }
        }
    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        Log.d("logs","Navigating to tol level destination: $topLevelDestination")
        Log.d("logs","popping up to : ${navController.graph.findStartDestination()}")
        Log.d("logs","Backstack: ${navController.graph.hierarchy.toList()}")

        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) { // pop everything out. Note: This kills all view models for the screens
                saveState = true
                inclusive=true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.MARKET -> {navController.navigateToMarket(topLevelNavOptions)}
            TopLevelDestination.INVENTORY -> {navController.navigateToInventoryHome(topLevelNavOptions)}
            TopLevelDestination.CHAT -> {navController.navigateToChatHome(topLevelNavOptions)}
            TopLevelDestination.SETTINGS -> {navController.navigateToSettingsHome(topLevelNavOptions)}
        }

    }
}
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

