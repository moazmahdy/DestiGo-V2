package com.mobilebreakero.home.components

import com.mobilebreakero.navigation_core.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.SCAN_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.home.R

sealed class Destinations(val route: String, val icon: Int) {
    object Home : Destinations(HOME_SCREEN,  R.drawable.home)
    object Trips : Destinations(TRIPS_SCREEN, R.drawable.traveling)
    object Scan : Destinations(SCAN_SCREEN, R.drawable.qrcode)
    object Profile : Destinations(PROFILE_SCREEN,  R.drawable.user)
}
