package com.neighborly.neighborlyandroid.market.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.neighborly.neighborlyandroid.market.ui.ui.theme.NeighborlyAndroidTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.login.ui.LoginViewModel

data class BottomNavigationItem(
    val title:String,
    val selectedIcon:ImageVector,
    val unselectedIcon:ImageVector,
    val hasNews:Boolean= false
)

class MarketActivity : ComponentActivity() {
    private val viewModel: MarketViewModel by viewModels { MarketViewModel.Factory}
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeighborlyAndroidTheme {
                val selectedNavItem by viewModel.selectedNavItem.collectAsState()
                val bottomNavItems= listOf(
                    BottomNavigationItem("Market", selectedIcon =Icons.Filled.Home, unselectedIcon = Icons.Outlined.Search, hasNews = false),
                    BottomNavigationItem("Inventory", selectedIcon = Icons.Filled.Build, unselectedIcon = Icons.Outlined.Build, hasNews = false),
                    BottomNavigationItem("Settings", selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings, hasNews = false)
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavBar(bottomNavItems= bottomNavItems, selectedNavItem = selectedNavItem)
                        }
                    ) {
                        padding -> MarketScreen(marketViewModel = viewModel, modifier = Modifier.padding(padding))
                    }
                }
            }

        }
    }

}
@Composable
fun BottomNavBar(bottomNavItems:List<BottomNavigationItem>,selectedNavItem:Int){
    NavigationBar {
        bottomNavItems.forEachIndexed{i, item ->
            NavigationBarItem(
                selected = i == selectedNavItem,
                onClick = {Log.i("logs","Navigate to screen "+ item.title)},
                label = { Text(text = item.title) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.hasNews){
                                Badge()
                            }

                        }
                    ) {
                        Icon(imageVector = if (i == selectedNavItem){
                            item.selectedIcon
                        }else{
                            item.unselectedIcon
                        },
                            contentDescription = item.title)
                    }
                }
            )
        }
    }

}



