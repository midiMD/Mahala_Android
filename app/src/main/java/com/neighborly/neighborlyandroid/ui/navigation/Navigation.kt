package com.neighborly.neighborlyandroid.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.OtherHouses
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.neighborly.neighborlyandroid.ui.login.LoginScreen
import com.neighborly.neighborlyandroid.ui.market.MarketScreen
import com.neighborly.neighborlyandroid.ui.register.RegistrationScreen
import androidx.navigation.navigation
import com.neighborly.neighborlyandroid.ui.MahalaAppState
import com.neighborly.neighborlyandroid.ui.chat.ChatHomeScreen
import com.neighborly.neighborlyandroid.ui.chat.ChatScreen
import com.neighborly.neighborlyandroid.ui.chat.ChatViewModel
import com.neighborly.neighborlyandroid.ui.chat.ConvoViewModel
import com.neighborly.neighborlyandroid.ui.inventory.InventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.add.AddInventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.add.AddInventoryViewModel
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryViewModel
import com.neighborly.neighborlyandroid.ui.login.LoginViewModel
import com.neighborly.neighborlyandroid.ui.login.reset.PasswordResetScreen
import com.neighborly.neighborlyandroid.ui.login.reset.PasswordResetViewModel
import com.neighborly.neighborlyandroid.ui.market.MarketViewModel
import com.neighborly.neighborlyandroid.ui.settings.SettingsHomeScreen
import com.neighborly.neighborlyandroid.ui.settings.SettingsViewModel
import kotlin.reflect.KClass
import kotlinx.serialization.Serializable
@Serializable data object UnverifiedAddressRoute
@Serializable data object LoginRoute
@Serializable data object RegisterRoute
@Serializable data object ResetPasswordRoute
@Serializable data object MarketRoute
@Serializable data object ChatBaseRoute
@Serializable data class ChatRoomRoute(val roomId:Long)
@Serializable data object ChatHomeRoute
@Serializable data object InventoryBaseRoute
@Serializable data object InventoryHomeRoute
@Serializable data object InventoryViewRoute
@Serializable data object InventoryAddRoute
@Serializable data object SettingsBaseRoute
@Serializable data object SettingsHomeRoute
@Serializable data object PasswordChangeRoute



@Composable
fun MahalaNavHost(appState: MahalaAppState){
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<MarketRoute>{backStackEntry->
            Log.d("navigation","Backstack entry: $backStackEntry")
            Log.d("navigation","LocalViewModelStoreOwner: ${LocalViewModelStoreOwner.current}")
            Log.d("navigation","Navigating to MarketScreen")
            MarketScreen(onChatButtonClick = {appState.navigateToTopLevelDestination(TopLevelDestination.CHAT)},
                marketViewModel = viewModel(factory = MarketViewModel.Factory)
            )
        }

        composable<LoginRoute>{
            LoginScreen(
                onNavigateToRegister = {navController.navigate(RegisterRoute)},
                navigateToMarket = {appState.navigateToTopLevelDestination(TopLevelDestination.MARKET)},
                viewModel = viewModel(factory = LoginViewModel.Factory),
                onNavigateToResetPassword = {navController.navigate(ResetPasswordRoute) }
            )
        }
        composable<ResetPasswordRoute>{
            PasswordResetScreen(viewModel = viewModel(factory = PasswordResetViewModel.Factory))
        }
        composable<RegisterRoute>{
            RegistrationScreen(onNavigateToLogin = {navController.navigate(LoginRoute)})
        }
        navigation<ChatBaseRoute>(
            startDestination = ChatHomeRoute,
        ) {
            composable<ChatHomeRoute>{ backStackEntry ->
                Log.d("logs","Navigating to Chat Home ")
                // ViewModel scoped to the "chatGraph" subgraph
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ChatBaseRoute)
                }

                val viewModel: ChatViewModel = viewModel(parentEntry,factory = ChatViewModel.Factory)
                ChatHomeScreen(
                    onConvoClick = {convoId: Long ->  navController.navigateToConvo(convoId = convoId)},
                    viewModel = viewModel
                )
            }
            composable<ChatRoomRoute>{ backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ChatBaseRoute)
                }

                // Reuse the same ViewModel scoped to the "chatGraph" subgraph
                val viewModel: ConvoViewModel = viewModel(parentEntry,factory = ConvoViewModel.Factory)
                ChatScreen(
                    onNavigateToChatHome = { appState.navigateToTopLevelDestination(TopLevelDestination.CHAT) },
                    viewModel = viewModel
                )
            }
        }
        navigation<InventoryBaseRoute>(startDestination = InventoryHomeRoute) {
            composable<InventoryHomeRoute> {
                InventoryScreen(
                    onNavigateToInventoryView = {navController.navigate(InventoryViewRoute)},
                    onNavigateToInventoryAdd = {navController.navigate(InventoryAddRoute)}
                )
            }

            composable<InventoryViewRoute>{backStackEntry->
                ViewInventoryScreen(modifier = Modifier.padding(0.dp),
                    viewModel = viewModel(factory = ViewInventoryViewModel.Factory) // scoped to this particular navigation destination
                )
            }
            composable<InventoryAddRoute>{
                (
                    AddInventoryScreen(
                        navigateToInventoryHome = { appState.navigateToTopLevelDestination(TopLevelDestination.INVENTORY) },
                        viewModel = viewModel(factory = AddInventoryViewModel.Factory) // scoped to the particular nav destination
                    )
                )
            }
        }
        navigation<SettingsBaseRoute>(startDestination = SettingsHomeRoute){
            composable<SettingsHomeRoute> { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(SettingsBaseRoute)
                }
                val navOptions = navOptions {
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
                val viewModel:SettingsViewModel = viewModel(parentEntry, factory = SettingsViewModel.Factory)
                SettingsHomeScreen(
                    modifier = Modifier,
                    viewModel = viewModel,
                    navigateToLogin = {
                        navController.navigate(LoginRoute,navOptions) // navigate to login page and empty the backstack
                    }
                )
            }


        }



    }

}
// when navigating to Top Level Destination, use appState.navigateToTopLevelDestination()
fun NavController.navigateToMarket(navOptions: NavOptions) = navigate(route = MarketRoute, navOptions)
fun NavController.navigateToInventoryHome(navOptions: NavOptions) = navigate(route = InventoryHomeRoute, navOptions)
fun NavController.navigateToChatHome(navOptions: NavOptions) = navigate(route = ChatHomeRoute, navOptions)
fun NavController.navigateToConvo(convoId: Long, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = ChatRoomRoute(convoId)) {
        navOptions()
    }
}
fun NavController.navigateToSettingsHome(navOptions: NavOptions) = navigate(route = SettingsBaseRoute,navOptions)


fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false

// displayed in bottom nav bar
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route:KClass<*>,
    val baseRoute:KClass<*> = route,
) {
    MARKET(
        selectedIcon = Icons.Filled.OtherHouses,
        unselectedIcon =Icons.Outlined.OtherHouses,
        route =MarketRoute::class
    ),
    INVENTORY(
        selectedIcon = Icons.Filled.Build,
        unselectedIcon = Icons.Outlined.Build,
        route = InventoryHomeRoute::class,
        baseRoute= InventoryBaseRoute::class,
    ),
    CHAT(
        selectedIcon = Icons.Filled.ChatBubble,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        route = ChatHomeRoute::class,
        baseRoute = ChatBaseRoute::class,
    ),
    SETTINGS(
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = SettingsBaseRoute::class,
    ),
}