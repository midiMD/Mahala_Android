package com.neighborly.neighborlyandroid.ui.navigation
import android.content.Context
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.House
import androidx.compose.material.icons.outlined.OtherHouses
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neighborly.neighborlyandroid.ui.login.LoginScreen
import com.neighborly.neighborlyandroid.ui.market.MarketScreen
import com.neighborly.neighborlyandroid.ui.register.RegistrationScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navigation
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.MarketItem
import com.neighborly.neighborlyandroid.ui.inventory.InventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.add.AddInventoryScreen
import com.neighborly.neighborlyandroid.ui.inventory.view.ViewInventoryScreen
import com.neighborly.neighborlyandroid.ui.market.item_detail.MarketItemDetailScreen

sealed class Screen(val route: String) {

    data object Login : Screen("login")
    data object Register:Screen("register")


    data object Market: Screen("market")
    data object MarketItemDetails: Screen("market/{$ARG_MARKET_ITEM_URI}"){
        fun createRoute(item: MarketItem) = "market/${item.id}"
    }
    data object Inventory:Screen("inventory/landing")
    data object ViewInventory:Screen("inventory/view")
    data object InventoryItemDetails: Screen("inventory/view/{$ARG_INVENTORY_ITEM_URI}"){
        fun createRoute(inventoryItem: InventoryItem) = "inventory/view/$inventoryItem.id"
    }
    data object AddInventory:Screen("inventory/add")
    data object Settings:Screen("settings")
    data object Chat:Screen("chat")
    companion object {
        val ARG_MARKET_ITEM_URI = "marketItem"
        val ARG_INVENTORY_ITEM_URI = "inventoryItem"

    }
}
val inventoryRoute = "inventory"
@Composable
fun MahalaNavHost(navController: NavHostController,
                  navigateToMarketItemDetail:(item:MarketItem, from:NavBackStackEntry)->Unit){
    // build the nav graph for the entire app. this will envelop
    val onNavigateToScreen:(screen:Screen)->Unit = {screen:Screen-> navController.navigate(screen.route)}
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Market.route) { backStackEntry ->
            MarketScreen()
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToScreen = {screen->onNavigateToScreen(screen)}
            )
        }
        composable(Screen.Register.route) {
            RegistrationScreen(onNavigateToScreen = {screen->onNavigateToScreen(screen)})
        }
        navigation(route = inventoryRoute, startDestination = Screen.Inventory.route) {
            composable(Screen.Inventory.route) {
                InventoryScreen(onNavigateToScreen = {screen->onNavigateToScreen(screen)})
            }
            composable(Screen.ViewInventory.route) {
                (
                    ViewInventoryScreen(onNavigateToScreen = {screen -> onNavigateToScreen(screen)})
                )
            }
            composable(Screen.AddInventory.route) {
                (
                    AddInventoryScreen(onNavigateToScreen = {screen -> onNavigateToScreen(screen)})
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
    TopLevelRoute("",Screen.Inventory,selectedIcon = Icons.Filled.Build, unselectedIcon = Icons.Outlined.Build, hasNews = false),
    TopLevelRoute("",Screen.Chat,selectedIcon = Icons.Filled.ChatBubble, unselectedIcon = Icons.Outlined.ChatBubbleOutline, hasNews = false),
    TopLevelRoute("",Screen.Settings,selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings, hasNews = false),
)
val showNavBarRoutes = listOf(Screen.Chat.route,Screen.Market.route,Screen.Inventory.route,Screen.Settings.route, Screen.Inventory.route,Screen.AddInventory.route,Screen.ViewInventory.route)

