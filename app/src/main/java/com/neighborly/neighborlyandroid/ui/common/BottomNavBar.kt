package com.neighborly.neighborlyandroid.ui.common

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.neighborly.neighborlyandroid.ui.navigation.TopLevelRoute


//@Composable
//fun BottomNavBar(topLevelRoutes:List<TopLevelRoute>,selectedTopLevelRoute:TopLevelRoute,onChange:(newRoute:TopLevelRoute)->Unit){
//
//    NavigationBar {
//        bottomNavItems.forEachIndexed{i, item ->
//            NavigationBarItem(
//                selected = (i == selectedNavItem),
//                onClick = { Log.i("logs","Navigate to screen "+ item.title)
//                          if (i!=selectedNavItem){
//                              onChange(i)
//                          }},
//                label = { Text(text = item.title) },
//                icon = {
//                    BadgedBox(
//                        badge = {
//                            if (item.hasNews){
//                                Badge()
//                            }
//
//                        }
//                    ) {
//                        Icon(imageVector = if (i == selectedNavItem){
//                            item.selectedIcon
//                        }else{
//                            item.unselectedIcon
//                        },
//                            contentDescription = item.title)
//                    }
//                }
//            )
//        }
//    }
//
//}