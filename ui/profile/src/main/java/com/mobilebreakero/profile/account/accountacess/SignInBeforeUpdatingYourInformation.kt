package com.mobilebreakero.profile.account.accountacess

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.navigation_core.NavigationRoutes.ACCOUNT_ACCESS_SETTINGS
import com.mobilebreakero.navigation_core.NavigationRoutes.SEND_CONFIRMATION_CODE
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.auth_domain.util.Utils
import com.mobilebreakero.auth_domain.util.Utils.Companion.showMessage
import com.mobilebreakero.profile.component.AuthTextField
import com.mobilebreakero.profile.component.PasswordTextField

@Composable
fun SignInBeforeUpdatingYourInformation(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,

        ) {

        val isSignInClicked = remember { mutableStateOf(false) }

        Text(
            text = "You Must Sign In again in order to update your information",
            fontSize = 20.sp,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
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
        val context = LocalContext.current

        com.mobilebreakero.core_ui.components.DestiGoButton(
            onClick = {
                viewModel.signInWithEmailAndPassword(
                    email = emailText.trim().lowercase(),
                    password = passwordText,
                    context = context
                )
                isSignInClicked.value = true
            },
            buttonColor = Color(0xff4F80FF),
            text = "LogIn",
            modifier = Modifier
                .shadow(elevation = 0.dp, shape = CircleShape)
                .width(270.dp)
                .height(50.dp)
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 2.dp)
                .align(CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(33.dp))

        Text(
            text = "Forgot Password?",
            color = Color(0xff4F80FF),
            modifier = Modifier.clickable { navController.navigate(SEND_CONFIRMATION_CODE) })

        if (isSignInClicked.value) {
            SignIn(
                showErrorMessage = { errorMessage ->
                    showMessage(context, errorMessage)
                },
                navController = navController
            )
        }
    }
}

@Composable
fun SignIn(
    viewModel: SignInViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit,
    navController: NavController
) {

    when (val signInResponse = viewModel.signInResponse) {
        is Response.Loading -> com.mobilebreakero.core_ui.components.LoadingIndicator()
        is Response.Success -> {
            navController.navigate(ACCOUNT_ACCESS_SETTINGS)
        }

        is Response.Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                Utils.print(e)
                showErrorMessage(e.message)
            }
        }

        else -> {

        }
    }
}