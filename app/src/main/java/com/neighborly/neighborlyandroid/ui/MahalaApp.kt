package com.neighborly.neighborlyandroid.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.neighborly.neighborlyandroid.ui.inventory.add.AddInventoryViewModel
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryViewModel
import com.neighborly.neighborlyandroid.ui.login.LoginScreen
import com.neighborly.neighborlyandroid.ui.market.MarketScreen
import com.neighborly.neighborlyandroid.ui.navigation.MahalaNavHost
import com.neighborly.neighborlyandroid.ui.navigation.isRouteInHierarchy
import com.neighborly.neighborlyandroid.ui.navigation.showNavBarRoutes
import com.neighborly.neighborlyandroid.ui.register.RegistrationScreen
import kotlinx.coroutines.launch

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState>{
    error("No Snackbar Host State")
}
@Composable
@Preview
fun MahalaApp(
    appState:MahalaAppState = rememberMahalaAppState()
) {

    val snackbarHostState = remember{appState.snackbarHostState}
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination  // apropo, this is also reactive because it is derived from state. So it acts as a state
//    Log.d("logs","Current destination: ${currentDestination.toString()}")
//    Log.d("logs","Current Top level destination: ${appState.currentTopLevelDestination.toString()}")
//    if (currentDestination != null) {
//        Log.d("logs","Hierarchy for $currentDestination")
//        currentDestination.hierarchy.forEach {
//            Log.d("logs",it.toString())
//        }
//    }
    CompositionLocalProvider(
        values = arrayOf(
            LocalSnackbarHostState provides snackbarHostState
        )
    ) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                //if (appState.currentDestination != null && appState.currentDestination!!.route in showNavBarRoutes) {

                if (appState.currentTopLevelDestination != null) {
                    BottomNavigation(
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        appState.topLevelDestinations.forEach { topLevelDestination ->

                            val isSelected = currentDestination
                                .isRouteInHierarchy(topLevelDestination.baseRoute)
                            BottomNavigationItem(
                                icon = {
                                    Icon(contentDescription = topLevelDestination.name,
                                        imageVector = if (isSelected){
                                           topLevelDestination.selectedIcon
                                        }else{
                                            topLevelDestination.unselectedIcon
                                        })
                                },
                                label = null, // don't display the name of the destinatin under the icon
                                selected = isSelected,
                                onClick = {
                                    //Log.d("logs","Clicked on  bottom nav bar item ${topLevelDestination.name}")
                                    appState.navigateToTopLevelDestination(topLevelDestination)
                                }
                            )
                        }

                    }

                }
            },
            snackbarHost = { SnackbarHost(LocalSnackbarHostState.current) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                //create the navController for the whole application
                MahalaNavHost(
                    appState= appState
                )
            }
        }

    }
    }

}



