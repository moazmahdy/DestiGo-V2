package com.mobilebreakero.auth.ui.verification.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.auth.R
import com.mobilebreakero.common_ui.components.AuthButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.auth.ui.common.components.AuthContent
import com.mobilebreakero.auth.ui.common.components.MainViewModel
import com.mobilebreakero.auth.ui.signup.SignUpViewModel
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.INTERESTED_PLACES_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.START_SCREEN
import com.mobilebreakero.auth_domain.util.Utils.Companion.showMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EmailVerificationScreenContent(
    navController: NavController,
    sendvirIficationViewModel: SignUpViewModel = hiltViewModel(),
    viewModel: MainViewModel = hiltViewModel()
) {
    AuthContent()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.confirmation),
            modifier = Modifier.size(160.dp),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Confirmation"
        )
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Confirm your email",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "We have sent a message to your email address. \n" +
                    "Please follow it and confirm your email to continue.",
            color = Color(0xffB3B3B3),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        AuthButton(
            onClick = {
                viewModel.reloadUser()
            },
            text = "I confirmed my email",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .width(290.dp)
                .height(45.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp, vertical = 2.dp),
        )

        Spacer(modifier = Modifier.height(33.dp))

        Text(
            text = "return to home",
            color = Color(0xffB3B3B3),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                viewModel.signOut()
                navController.navigate(route = START_SCREEN)
            }
        )

        Spacer(modifier = Modifier.height(33.dp))
        val coroutineScope = rememberCoroutineScope()

        Text(
            text = "Send email again",
            color = Color(0xffB3B3B3),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                coroutineScope.launch {
                    delay(1000)
                    sendvirIficationViewModel.sendEmailVerification()
                }
            }
        )

    }

    val context = LocalContext.current

    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                navController.navigate(INTERESTED_PLACES_SCREEN)
            } else {
                showMessage(context, "Email is not verified yet")
            }
        }
    )
}