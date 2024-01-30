package com.mobilebreakero.auth.ui.start.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.auth.ui.start.StartViewModel
import com.mobilebreakero.auth_domain.util.Response.Success
import com.mobilebreakero.auth_domain.util.Response.Loading
import com.mobilebreakero.auth_domain.util.Response.Failure

@Composable
fun SignInAnon(
    viewModel: StartViewModel = hiltViewModel()
) {
    when(val signInResponse = viewModel.signInResponse) {
        is Loading -> CircularProgressIndicator()
        is Success -> Unit
        is Failure -> LaunchedEffect(Unit) {
            print(signInResponse.e)
        }
    }
}