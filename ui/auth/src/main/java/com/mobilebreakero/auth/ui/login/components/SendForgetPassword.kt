package com.mobilebreakero.auth.ui.login.components

import androidx.compose.foundation.layout.Column
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
import com.mobilebreakero.auth.ui.common.components.AuthContent
import com.mobilebreakero.auth.ui.common.components.AuthTextField
import com.mobilebreakero.auth.ui.login.LoginViewModel
import com.mobilebreakero.common_ui.components.AuthButton
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_IN_SCREEN
import com.mobilebreakero.auth_domain.util.Utils


@Composable
fun SendForgetPassword(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Column {
        AuthContent()

        var emailText by remember { mutableStateOf("") }
        AuthTextField(
            text = emailText,
            onValueChange = { emailText = it },
            label = "type your email"
        )
        Spacer(modifier = Modifier.height(20.dp))
        val context = LocalContext.current

        AuthButton(
            onClick = {
                viewModel.sendPasswordResetEmail(
                    email = emailText.trim().lowercase(),
                )
            },
            buttonColor = Color(0xff4F80FF),
            text = "Send An Email",
            modifier = Modifier
                .shadow(elevation = 0.dp, shape = CircleShape)
                .width(270.dp)
                .height(50.dp)
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(33.dp))

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