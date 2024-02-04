package com.mobilebreakero.ui.login.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.auth_domain.util.Response.Loading
import com.mobilebreakero.auth_domain.util.Response.Success
import com.mobilebreakero.auth_domain.util.Response.Failure
import com.mobilebreakero.auth_domain.util.Utils.Companion.print
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Color
import com.mobilebreakero.ui.login.LoginViewModel


@Composable
fun SignIn(
    viewModel: LoginViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit,
    navigateToHome: () -> Unit
) {

    when (val signInResponse = viewModel.signInResponse) {
        is Loading -> CircularProgressIndicator(color = Color.Blue)
        is Success -> {
            val isUserSignedIn = signInResponse.data
            LaunchedEffect(isUserSignedIn) {
                if (isUserSignedIn) {
                    navigateToHome()
                }
            }
        }

        is Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }

        else -> {}
    }
}

@Composable
fun ForgetPasswordEmailSend(
    viewModel: LoginViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit,
    navigateToLogin: () -> Unit
) {

    when (val sendPasswordRespones = viewModel.forgetPasswordEmailResponse) {
        is Loading -> CircularProgressIndicator(color = Color.Blue)
        is Success -> {
            val isEmailSent = sendPasswordRespones.data
            LaunchedEffect(isEmailSent) {
                if (isEmailSent) {
                    navigateToLogin()
                }
            }
        }
        is Failure -> sendPasswordRespones.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }

        else -> {}
    }
}