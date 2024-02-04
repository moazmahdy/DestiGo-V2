package com.mobilebreakero.ui.signup.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.auth_domain.util.Utils.Companion.showMessage
import com.mobilebreakero.core_ui.components.DestiGoButton
import com.mobilebreakero.core_ui.components.VSpacer
import com.mobilebreakero.core_ui.components.buttonContainerColor
import com.mobilebreakero.core_ui.design_system.MainHeight.mediumHeight50
import com.mobilebreakero.core_ui.design_system.MainWidth.largeWidth250
import com.mobilebreakero.core_ui.design_system.Padding.large20
import com.mobilebreakero.core_ui.design_system.Padding.small5
import com.mobilebreakero.navigation_core.NavigationRoutes.EMAIL_VERIFICATION_SCREEN
import com.mobilebreakero.ui.common.components.AuthContent
import com.mobilebreakero.ui.common.components.DestiGoTextField
import com.mobilebreakero.ui.common.components.PasswordTextField
import com.mobilebreakero.ui.signup.SignUpViewModel
import com.mobilebreakero.ui.signup.components.ButtonModifier.mainModifier

object ButtonModifier {
    val mainModifier =  Modifier
        .shadow(elevation = 0.dp, shape = CircleShape)
        .width(largeWidth250)
        .height(mediumHeight50)
        .padding(horizontal = large20, vertical = small5)
}

@Composable
fun SignUpWithEmailAndPassword(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {

    AuthContent()

    var usernameText by remember { mutableStateOf("") }
    DestiGoTextField(
        text = usernameText,
        onValueChange = { usernameText = it },
        label = "Full Name"
    )

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

    DestiGoButton(
        onClick = {
            viewModel.signUpWithEmailAndPassword(
                name = usernameText.trim().lowercase(),
                email = emailText.trim().lowercase(),
                password = passwordText,
            )
        },
        buttonColor = buttonContainerColor,
        text = "Sign Up",
        modifier = mainModifier
    )

    val context = LocalContext.current

    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification()
            navController.navigate(EMAIL_VERIFICATION_SCREEN)
        },
        showVerifyEmailMessage = {
            showMessage(context, "We've sent you an email with a link to verify the email.")
        }
    )

    SendEmailVerification()
}
