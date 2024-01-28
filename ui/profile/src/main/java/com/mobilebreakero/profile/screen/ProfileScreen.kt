package com.mobilebreakero.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mobilebreakero.profile.component.ProfileSection


@Composable
fun ProfileScreen(navController: NavController) {
    Column {
        ProfileSection(navController = navController)
    }
}
