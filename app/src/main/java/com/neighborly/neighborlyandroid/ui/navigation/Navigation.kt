package com.neighborly.neighborlyandroid.ui.navigation

import android.util.Log
import androidx.annotation.StringRes
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neighborly.neighborlyandroid.ui.login.LoginScreen
import com.neighborly.neighborlyandroid.ui.market.MarketScreen
import com.neighborly.neighborlyandroid.ui.register.RegistrationScreen
import androidx.navigation.navigation
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.ui.MahalaAppState
import com.neighborly.neighborlyandroid.ui.chat.ChatHomeScreen
import com.neighborly.neighborlyandroid.ui.chat.ChatScreen
import com.neighborly.neighborlyandroid.ui.chat.ChatViewModel
import com.neighborly.neighborlyandroid.ui.inventory.InventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.add.AddInventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.add.AddInventoryViewModel
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryViewModel
import com.neighborly.neighborlyandroid.ui.login.LoginViewModel
import kotlin.reflect.KClass
import kotlinx.serialization.Serializable

@Serializable data object LoginRoute
@Serializable data object RegisterRoute
@Serializable data object MarketRoute
@Serializable data object ChatBaseRoute
@Serializable data object ChatRoute
@Serializable data object ChatHomeRoute
@Serializable data object InventoryBaseRoute
@Serializable data object InventoryHomeRoute
@Serializable data object InventoryViewRoute
@Serializable data object InventoryAddRoute
@Serializable data object SettingsRoute

@Composable
fun MahalaNavHost(appState: MahalaAppState
                  ){
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<MarketRoute>{
            MarketScreen(onChatButtonClick = {appState.navigateToTopLevelDestination(TopLevelDestination.CHAT)})
        }

        composable<LoginRoute>{
            LoginScreen(
                onNavigateToRegister = {navController.navigate(RegisterRoute)},
                navigateToMarket = {appState.navigateToTopLevelDestination(TopLevelDestination.MARKET)},
                viewModel = viewModel(factory = LoginViewModel.Factory)
            )
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
                val viewModel: ChatViewModel = viewModel(parentEntry, factory = ChatViewModel.Factory)
                ChatHomeScreen(
                    onNavigateToChatScreen = { navController.navigate(ChatRoute) },
                    viewModel = viewModel
                )
            }
            composable<ChatRoute>{ backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ChatBaseRoute)
                }

                // Reuse the same ViewModel scoped to the "chatGraph" subgraph
                val viewModel: ChatViewModel = viewModel(parentEntry,factory = ChatViewModel.Factory)
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
                ViewInventoryScreen(
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



    }

}
fun NavController.navigateToMarket(navOptions: NavOptions) = navigate(route = MarketRoute, navOptions)
fun NavController.navigateToInventoryHome(navOptions: NavOptions) = navigate(route = InventoryHomeRoute, navOptions)
fun NavController.navigateToChatHome(navOptions: NavOptions) = navigate(route = ChatHomeRoute, navOptions)


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
    val childRoutes:List<KClass<*>> = emptyList()
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
        childRoutes = listOf(InventoryHomeRoute::class,InventoryAddRoute::class,InventoryViewRoute::class)
    ),
    CHAT(
        selectedIcon = Icons.Filled.ChatBubble,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        route = ChatHomeRoute::class,
        baseRoute = ChatBaseRoute::class,
        childRoutes = listOf(ChatRoute::class,ChatHomeRoute::class)
    ),
    SETTINGS(
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = SettingsRoute::class,
    ),
}