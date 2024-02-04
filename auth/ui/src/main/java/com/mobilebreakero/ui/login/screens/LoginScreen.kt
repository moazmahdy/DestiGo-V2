package com.mobilebreakero.ui.login.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.navigation.NavController
import com.mobilebreakero.core_ui.design_system.Modifiers.fillMaxSize
import com.mobilebreakero.ui.login.components.SignInWithEmailAndPasswordScreenContent


@Composable
fun LoginScreen (navController: NavController) {

    Box(modifier = fillMaxSize) {

        Column(
            modifier = fillMaxSize,
            horizontalAlignment = CenterHorizontally
        ) {
            SignInWithEmailAndPasswordScreenContent(navController = navController)
        }
    }
}