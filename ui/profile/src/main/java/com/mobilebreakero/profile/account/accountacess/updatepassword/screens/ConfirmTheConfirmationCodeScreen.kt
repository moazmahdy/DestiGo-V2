package com.mobilebreakero.profile.account.accountacess.updatepassword.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.navigation_core.NavigationRoutes.CHOOSE_NEW_PASSWORD
import com.mobilebreakero.navigation_core.NavigationRoutes.START_SCREEN
import com.mobilebreakero.profile.account.accountacess.components.ConfirmTheConfirmationCodeScreenContent

@Composable
fun ConfirmTheConfirmationCodeScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ConfirmTheConfirmationCodeScreenContent(
            navController = navController,
            CHOOSE_NEW_PASSWORD,
            START_SCREEN,
            CHOOSE_NEW_PASSWORD
        )
    }
}