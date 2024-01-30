package com.mobilebreakero.auth.ui.verification.components

import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.auth.ui.common.components.MainViewModel
import com.mobilebreakero.auth_domain.util.Response.Failure
import com.mobilebreakero.auth_domain.util.Response.Loading
import com.mobilebreakero.auth_domain.util.Response.Success
import com.mobilebreakero.auth_domain.util.Utils.Companion.print

@Composable
fun ReloadUser(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {

    when(val reloadUserResponse = viewModel.reloadUserResponse) {
        is Loading -> CircularProgressIndicator(modifier = Modifier.background(Color.Blue))
        is Success -> {
            val isUserReloaded = reloadUserResponse.data
            LaunchedEffect(isUserReloaded) {
                if (isUserReloaded) {
                    navigateToProfileScreen()
                }
            }
        }
        is Failure -> reloadUserResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }

        else -> {}
    }
}