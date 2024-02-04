package com.mobilebreakero.ui.login.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.auth_domain.util.Utils.Companion.showMessage
import com.mobilebreakero.core_ui.components.DestiGoButton
import com.mobilebreakero.core_ui.components.VSpacer
import com.mobilebreakero.core_ui.components.buttonContainerColor
import com.mobilebreakero.core_ui.design_system.Dimens.extraLarge30
import com.mobilebreakero.core_ui.design_system.Dimens.large20
import com.mobilebreakero.navigation_core.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.SEND_FORGET_PASSWORD_EMAIL
import com.mobilebreakero.ui.common.components.AuthContent
import com.mobilebreakero.ui.common.components.DestiGoTextField
import com.mobilebreakero.ui.common.components.PasswordTextField
import com.mobilebreakero.ui.login.LoginViewModel
import com.mobilebreakero.ui.signup.components.ButtonModifier.mainModifier

@Composable
fun SignInWithEmailAndPasswordScreenContent(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    AuthContent()

    var emailText by remember { mutableStateOf("") }
    DestiGoTextField(
        text = emailText,
        onValueChange = { emailText = it },
        label = "Email"
    )

    var passwordText by remember { mutableStateOf("") }
    PasswordTextField(
        onValueChange = { passwordText = it },
    )

    VSpacer(height = large20)
    val context = LocalContext.current

    DestiGoButton(
        onClick = {
            viewModel.signInWithEmailAndPassword(
                email = emailText.trim().lowercase(),
                password = passwordText,
            )
        },
        buttonColor = buttonContainerColor,
        text = "LogIn",
        modifier = mainModifier

    )

    VSpacer(height = extraLarge30)
    Text(
        text = "Forgot Password?",
        color = buttonContainerColor,
        modifier = Modifier.clickable {
            navController.navigate(SEND_FORGET_PASSWORD_EMAIL)
        }
    )


    SignIn(
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        },
        navigateToHome = {
            navController.navigate(HOME_SCREEN)
        }
    )

}