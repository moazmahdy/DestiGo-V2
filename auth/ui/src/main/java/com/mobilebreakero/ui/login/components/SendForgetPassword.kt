package com.mobilebreakero.ui.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.auth_domain.util.Utils
import com.mobilebreakero.core_ui.components.DestiGoButton
import com.mobilebreakero.core_ui.components.VSpacer
import com.mobilebreakero.core_ui.components.buttonContainerColor
import com.mobilebreakero.core_ui.design_system.Dimens.extraLarge30
import com.mobilebreakero.core_ui.design_system.Dimens.large20
import com.mobilebreakero.navigation_core.NavigationRoutes.SIGN_IN_SCREEN
import com.mobilebreakero.ui.common.components.AuthContent
import com.mobilebreakero.ui.common.components.DestiGoTextField
import com.mobilebreakero.ui.login.LoginViewModel
import com.mobilebreakero.ui.signup.components.ButtonModifier.mainModifier


@Composable
fun SendForgetPassword(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Column {
        AuthContent()

        var emailText by remember { mutableStateOf("") }
        DestiGoTextField(
            text = emailText,
            onValueChange = { emailText = it },
            label = "type your email"
        )
        VSpacer(height = large20)
        val context = LocalContext.current

        DestiGoButton(
            onClick = {
                viewModel.sendPasswordResetEmail(
                    email = emailText.trim().lowercase(),
                )
            },
            buttonColor =buttonContainerColor,
            text = "Send An Email",
            modifier = mainModifier
        )

        VSpacer(height = extraLarge30)

        ForgetPasswordEmailSend(
            showErrorMessage = { errorMessage ->
                Utils.showMessage(context, errorMessage)
            },
            navigateToLogin = {
                navController.navigate(SIGN_IN_SCREEN) {
                    popUpTo(SIGN_IN_SCREEN) {
                        inclusive = true
                    }
                }
            }
        )
    }
}