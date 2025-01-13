package com.neighborly.neighborlyandroid.ui.navigation

import android.util.Log
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neighborly.neighborlyandroid.ui.login.LoginScreen
import com.neighborly.neighborlyandroid.ui.market.MarketScreen
import com.neighborly.neighborlyandroid.ui.register.RegistrationScreen
import androidx.navigation.navigation
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.ui.chat.ChatHomeScreen
import com.neighborly.neighborlyandroid.ui.chat.ChatScreen
import com.neighborly.neighborlyandroid.ui.chat.ChatViewModel
import com.neighborly.neighborlyandroid.ui.inventory.InventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.add.AddInventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.add.AddInventoryViewModel
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryViewModel

sealed class Screen(val route: String) {

    data object Login : Screen("login")
    data object Register:Screen("register")


    data object Market: Screen("market")
    data object MarketItemDetails: Screen("market/{$ARG_MARKET_ITEM_URI}"){
        fun createRoute(item: MarketItem) = "market/${item.id}"
    }
    data object InventoryGraph:Screen(inventorySubgraphRoute)
    data object Inventory:Screen("$inventorySubgraphRoute/landing")
    data object ViewInventory:Screen("$inventorySubgraphRoute/view")
    data object InventoryItemDetails: Screen("$inventorySubgraphRoute/view/{$ARG_INVENTORY_ITEM_URI}"){
        fun createRoute(inventoryItem: InventoryItem) = "$inventorySubgraphRoute/view/$inventoryItem.id"
    }
    data object AddInventory:Screen("$inventorySubgraphRoute/add")
    data object Settings:Screen("settings")

    data object ChatGraph:Screen(chatSubgraphRoute) // parent node of the navigation subgraph containing Chat and ChatHome
    data object ChatHome:Screen("${chatSubgraphRoute}/chatHome")
    data object Chat:Screen("${chatSubgraphRoute}/chat")
    companion object {
        val ARG_MARKET_ITEM_URI = "marketItem"
        val ARG_INVENTORY_ITEM_URI = "inventoryItem"

    }
}
val inventorySubgraphRoute = "inventory"
val chatSubgraphRoute = "chatGraph"
@Composable
fun MahalaNavHost(navController: NavHostController
                  ){
    // build the nav graph for the entire app. this will envelop
    val onNavigateToScreen:(screen:Screen)->Unit = {screen:Screen-> navController.navigate(screen.route)}

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Market.route) {
            MarketScreen(navigateToChat = {onNavigateToScreen(Screen.ChatGraph)})
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToScreen = {screen->onNavigateToScreen(screen)}
            )
        }
        composable(Screen.Register.route) {
            RegistrationScreen(onNavigateToScreen = {screen->onNavigateToScreen(screen)})
        }
        navigation(
            startDestination = Screen.ChatHome.route,
            route = Screen.ChatGraph.route
        ) {
            composable(Screen.ChatHome.route) { backStackEntry ->
                Log.d("logs","Navigating to Chat Home ")
                // ViewModel scoped to the "chatGraph" subgraph
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(chatSubgraphRoute)
                }
                val viewModel: ChatViewModel = viewModel(parentEntry, factory = ChatViewModel.Factory)
                ChatHomeScreen(
                    onNavigateToChatScreen = { navController.navigate(Screen.Chat.route) },
                    viewModel = viewModel
                )
            }
            composable(Screen.Chat.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("chatGraph")
                }

                // Reuse the same ViewModel scoped to the "chatGraph" subgraph
                val viewModel: ChatViewModel = viewModel(parentEntry,factory = ChatViewModel.Factory)
                ChatScreen(
                    onNavigateToChatHome = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
        }
        navigation(route = Screen.InventoryGraph.route, startDestination = Screen.Inventory.route) {
            composable(Screen.Inventory.route) {
                InventoryScreen(onNavigateToScreen = {screen->onNavigateToScreen(screen)})
            }
            composable(Screen.ViewInventory.route) {backStackEntry->
                ViewInventoryScreen(
                    viewModel = viewModel(factory = ViewInventoryViewModel.Factory) // scoped to this particular navigation destination
                )
            }
            composable(Screen.AddInventory.route) {
                (
                    AddInventoryScreen(
                        onNavigateToInventoryHome = { onNavigateToScreen(Screen.Inventory) },
                        viewModel = viewModel(factory = AddInventoryViewModel.Factory) // scoped to the particular nav destination
                    )
                )
            }
        }



    }

}

// displayed in bottom nav bar
data class TopLevelRoute(
    val name:String,
    val screen: Screen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews:Boolean)


val topLevelRoutes:List<TopLevelRoute> = listOf(
    TopLevelRoute("",Screen.Market, selectedIcon = Icons.Filled.OtherHouses, unselectedIcon = Icons.Outlined.OtherHouses, hasNews = false),
    TopLevelRoute("",Screen.InventoryGraph,selectedIcon = Icons.Filled.Build, unselectedIcon = Icons.Outlined.Build, hasNews = false),
    TopLevelRoute("",Screen.ChatGraph,selectedIcon = Icons.Filled.ChatBubble, unselectedIcon = Icons.Outlined.ChatBubbleOutline, hasNews = false),
    TopLevelRoute("",Screen.Settings,selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings, hasNews = false),
)
val showNavBarRoutes = listOf(Screen.Chat.route, Screen.ChatHome.route,
    chatSubgraphRoute,Screen.Market.route,Screen.Inventory.route,Screen.Settings.route, Screen.InventoryGraph.route,Screen.AddInventory.route,Screen.ViewInventory.route)

