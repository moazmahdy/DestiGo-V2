package com.mobilebreakero.home.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SCAN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN

@Composable
fun BottomNavigation(navController: NavController) {

    val items = listOf(
        Destinations.Scan,
        Destinations.Home,
        Destinations.Trips,
        Destinations.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val Barcolor = Color(0xFFF2F5FF)

    val routeNames = listOf(
        SCAN_SCREEN,
        HOME_SCREEN,
        TRIPS_SCREEN,
        PROFILE_SCREEN
    )

    var selectedIndex by remember {
        mutableIntStateOf(1)
    }

    LaunchedEffect(currentRoute) {
        selectedIndex = items.indexOfFirst { item ->
            item.route == currentRoute
        }
    }

    if (currentRoute in routeNames) {
        AnimatedNavigationBar(
            modifier = Modifier
                .height(70.dp)
                .background(Color(0xFF303A56)),
            selectedIndex = selectedIndex,
            cornerRadius = shapeCornerRadius(cornerRadius = 15.dp),
            ballAnimation = Straight(tween(600)),
            indentAnimation = Height(tween(1000)),
            barColor = Barcolor,
            ballColor = Color(0xFFF2F5FF),
        ) {
            items.forEachIndexed { index, item ->
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    CustomButtonForButtonNav(
                        item = item,
                        isSelected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(item.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}