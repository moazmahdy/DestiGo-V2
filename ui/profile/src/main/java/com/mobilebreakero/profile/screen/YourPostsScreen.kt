package com.mobilebreakero.profile.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mobilebreakero.profile.yourposts.YourPostsScreenContent


@Composable
fun YourPostsScreen(navController: NavController) {
    YourPostsScreenContent(navController = navController)
}
