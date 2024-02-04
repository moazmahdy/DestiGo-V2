package com.mobilebreakero.ui.verification.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.ui.common.components.AuthContent
import com.mobilebreakero.ui.common.components.MainViewModel
import com.mobilebreakero.ui.signup.SignUpViewModel
import com.mobilebreakero.auth_domain.util.Utils.Companion.showMessage
import com.mobilebreakero.core_ui.components.DestiGoButton
import com.mobilebreakero.core_ui.components.VSpacer
import com.mobilebreakero.core_ui.components.buttonContainerColor
import com.mobilebreakero.core_ui.design_system.Colors
import com.mobilebreakero.core_ui.design_system.Colors.inputFieldContainerColor
import com.mobilebreakero.core_ui.design_system.Modifiers
import com.mobilebreakero.core_ui.design_system.Modifiers.fillMaxSize
import com.mobilebreakero.core_ui.design_system.SpacerHeights
import com.mobilebreakero.core_ui.design_system.SpacerHeights.extraLarge30
import com.mobilebreakero.core_ui.design_system.SpacerHeights.large20
import com.mobilebreakero.core_ui.design_system.TextStyles
import com.mobilebreakero.core_ui.design_system.TextStyles.HeaderTextStyle14
import com.mobilebreakero.navigation_core.NavigationRoutes.INTERESTED_PLACES_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.START_SCREEN
import com.mobilebreakero.ui.R
import com.mobilebreakero.ui.signup.components.ButtonModifier
import com.mobilebreakero.ui.signup.components.ButtonModifier.mainModifier
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
        modifier = fillMaxSize,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.confirmation),
            modifier = Modifier.size(160.dp),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Confirmation"
        )
        VSpacer(height = large20)
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

        VSpacer(height = large20)

        DestiGoButton(
            onClick = {
                viewModel.reloadUser()
            },
            text = "I confirmed my email",
            buttonColor = buttonContainerColor,
            modifier = mainModifier,
        )

        VSpacer(height = extraLarge30)

        Text(
            text = "return to home",
            color = Color(0xffB3B3B3),
            style = HeaderTextStyle14,
            modifier = Modifier.clickable {
                viewModel.signOut()
                navController.navigate(route = START_SCREEN)
            }
        )

        VSpacer(height = SpacerHeights.extraLarge30)
        val coroutineScope = rememberCoroutineScope()

        Text(
            text = "Send email again",
            color = inputFieldContainerColor,
            style = HeaderTextStyle14,
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