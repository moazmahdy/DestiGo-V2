package com.mobilebreakero.core_ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobilebreakero.core_ui.R
import com.mobilebreakero.navigation_core.NavigationRoutes.ADD_COMMENT
import com.mobilebreakero.navigation_core.NavigationRoutes.ADD_JOURNAL
import com.mobilebreakero.navigation_core.NavigationRoutes.ADD_PLACES_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.CHOOSE_COVER_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.DETAILS_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.PLACES_DETAILS_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.PLAN_CHECK_LIST
import com.mobilebreakero.navigation_core.NavigationRoutes.POSTS_DETAILS
import com.mobilebreakero.navigation_core.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.PUBLIC_TRIP_DETAILS
import com.mobilebreakero.navigation_core.NavigationRoutes.SCAN_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.SIGN_IN_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.SIGN_UP_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.START_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.TRIP_DETAILS
import com.mobilebreakero.navigation_core.NavigationRoutes.WELCOME_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestiGoTopAppBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val noTopBarList = mutableListOf(
        SIGN_IN_SCREEN, SIGN_UP_SCREEN, START_SCREEN, WELCOME_SCREEN
    )

    if (currentRoute !in noTopBarList) {
        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(),
            title = {
                TopBarTitle(navController = navController)
            },
            navigationIcon = {
                NavIcon(navController = navController)
            }
        )
    }

}

@Composable
fun NavIcon(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val routeNames = listOf(HOME_SCREEN, TRIPS_SCREEN, PROFILE_SCREEN, SCAN_SCREEN)

    if (currentRoute != null) {
        if (currentRoute !in routeNames && currentRoute.isNotEmpty()) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = "back",
                    tint = Color(0xFF4F80FF),
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

@Composable
fun TopBarTitle(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != null) {
        Text(
            text = if (currentRoute.isEmpty()) {
                ""
            } else if (currentRoute == DETAILS_SCREEN) {
                "Details"
            } else if (currentRoute == PLAN_CHECK_LIST) {
                "Plan Check List"
            } else if (currentRoute == ADD_PLACES_SCREEN) {
                "Places to Visit"
            } else if (currentRoute == CHOOSE_COVER_SCREEN) {
                "Choose Cover Image"
            } else if (currentRoute == HOME_SCREEN) {
                "Home"
            } else if (currentRoute == ADD_COMMENT) {
                "Add New Comment"
            } else if (currentRoute == POSTS_DETAILS) {
                "Post Details"
            } else if (currentRoute == PUBLIC_TRIP_DETAILS) {
                "Saved Trip Details"
            } else if (currentRoute == TRIP_DETAILS) {
                "Trip Details"
            } else if (currentRoute == ADD_JOURNAL) {
                "Trip Journal"
            } else if (currentRoute == PLACES_DETAILS_SCREEN) {
                "Place Details"
            } else {
                currentRoute.toString()
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4F80FF),
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis
        )
    }

}