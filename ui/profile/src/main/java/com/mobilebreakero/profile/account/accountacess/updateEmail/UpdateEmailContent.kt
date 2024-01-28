package com.mobilebreakero.profile.account.accountacess.updateEmail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.ACCOUNT_ACCESS_SETTINGS
import com.mobilebreakero.profile.account.accountacess.components.UpdateContent

@Composable
fun ChooseNewEmail(
    navController: NavController,
    viewModel: UpdateEmailViewModel = hiltViewModel()
) {

    val emaill = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UpdateContent(
            navController = navController,
            headerText = "Choose new email",
            header2 = "New Email",
            textField = "type a new email",
            buttonText = "Send a confirmation code",
            cancelText = "return to settings",
            cancelNav = ACCOUNT_ACCESS_SETTINGS,
            onClick = { email ->
                viewModel.updateEmail(email)
                emaill.value = email
            }
        )
        UpdateEmail(navController = navController)
    }
}