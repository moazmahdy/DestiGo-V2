package com.mobilebreakero.profile.account.accountacess.updateEmail


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.EMAIL_SENT_SUCCESSFULY
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.Response.Loading
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Utils.Companion.print
import com.mobilebreakero.domain.util.Utils.Companion.showMessage

@Composable
fun UpdateEmail(
    viewModel: UpdateEmailViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    when (val updateEmail = viewModel.updateEmailResponse) {
        is Loading -> LoadingIndicator()
        is Success -> {
            val isEmailUpdated = updateEmail.data
            LaunchedEffect(isEmailUpdated) {
                if (isEmailUpdated) {
                    showMessage(context, "Email Sent Successfully")
                    navController.navigate(EMAIL_SENT_SUCCESSFULY)
                }
            }
        }

        is Failure -> updateEmail.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }

        else -> {

        }
    }
}