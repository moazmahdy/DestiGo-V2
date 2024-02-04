package com.mobilebreakero.ui.verification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.ui.verification.components.EmailVerificationScreenContent

@Composable
fun EmailVerificationScreen(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        EmailVerificationScreenContent(navController = navController)
    }

}