package com.mobilebreakero.navigation

sealed class Screen(
    val route: String,
    val title: String
) {
    object Home : Screen("home", "Home")
    object Details : Screen("details", "Details")
}