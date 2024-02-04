package com.mobilebreakero.profile.account.accountacess.updatepassword.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.navigation_core.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.profile.account.accountacess.components.UpdatePassword
import com.mobilebreakero.profile.account.accountacess.components.UpdatePasswordContent
import com.mobilebreakero.profile.account.accountacess.updatepassword.PasswordResetViewModel

@Composable
fun ChooseNewPasswordScreen(
    navController: NavController,
    viewModel: PasswordResetViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UpdatePasswordContent(
            navController = navController,
            headerText = "Choose new password",
            header2 = "New password",
            textField = "type a new password",
            buttonText = "Set new password",
            cancelText = "return to Profile",
            cancelNav = PROFILE_SCREEN,
            onClick = { password ->
                viewModel.updatePassword(password = password)
            }
        )
    }

    UpdatePassword(navController = navController)
}