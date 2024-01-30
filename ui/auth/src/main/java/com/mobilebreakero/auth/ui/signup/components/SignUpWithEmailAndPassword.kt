package com.mobilebreakero.auth.ui.signup.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.components.AuthButton
import com.mobilebreakero.auth.ui.common.components.AuthContent
import com.mobilebreakero.auth.ui.common.components.AuthTextField
import com.mobilebreakero.auth.ui.common.components.PasswordTextField
import com.mobilebreakero.auth.ui.signup.SignUpViewModel
import com.mobilebreakero.auth_domain.util.Utils.Companion.showMessage
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.EMAIL_VERIFICATION_SCREEN

@Composable
fun SignUpWithEmailAndPassword(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {

    AuthContent()

    var usernameText by remember { mutableStateOf("") }
    AuthTextField(
        text = usernameText,
        onValueChange = { usernameText = it },
        label = "Full Name"
    )

    var emailText by remember { mutableStateOf("") }
    AuthTextField(
        text = emailText,
        onValueChange = { emailText = it },
        label = "Email"
    )

    var passwordText by remember { mutableStateOf("") }
    PasswordTextField(
        onValueChange = { passwordText = it },
    )

    Spacer(modifier = Modifier.height(20.dp))

    AuthButton(
        onClick = {
            viewModel.signUpWithEmailAndPassword(
                name = usernameText.trim().lowercase(),
                email = emailText.trim().lowercase(),
                password = passwordText,
            )
        },
        buttonColor = Color(0xff4F80FF),
        text = "Sign Up",
        modifier = Modifier
            .shadow(elevation = 0.dp, shape = CircleShape)
            .width(270.dp)
            .height(50.dp)
            .wrapContentHeight()
            .padding(horizontal = 20.dp, vertical = 2.dp)
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
