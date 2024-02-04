package com.mobilebreakero.ui.start.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.core_ui.components.DestiGoButton
import com.mobilebreakero.core_ui.components.VSpacer
import com.mobilebreakero.core_ui.design_system.Modifiers.fillMaxSize
import com.mobilebreakero.core_ui.design_system.Padding
import com.mobilebreakero.core_ui.design_system.SpacerHeights
import com.mobilebreakero.core_ui.design_system.SpacerHeights.extraLarge30
import com.mobilebreakero.core_ui.design_system.TextStyles.ContentTextStyle14
import com.mobilebreakero.navigation_core.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.SIGN_IN_SCREEN
import com.mobilebreakero.navigation_core.NavigationRoutes.SIGN_UP_SCREEN
import com.mobilebreakero.ui.R
import com.mobilebreakero.ui.signup.components.ButtonModifier
import com.mobilebreakero.ui.signup.components.ButtonModifier.mainModifier
import com.mobilebreakero.ui.start.StartViewModel
import com.mobilebreakero.ui.start.components.SignInAnon

@Composable
fun StartAuthScreen(viewModel: StartViewModel = hiltViewModel(), navController: NavController) {

    Box(
        modifier = fillMaxSize
    ) {
        Column(
            modifier = fillMaxSize
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mainauthimage),
                contentDescription = "This is main image in auth screen",
            )
            VSpacer(height = SpacerHeights.large20)
            Text(
                text = "Planning, Sharing, Enjoying Life",
                style = ContentTextStyle14,
                textAlign = Center,
                modifier = Modifier.padding(Padding.large20)
            )
            Text(
                text = "document your travel experiences in a digital journal. \n" +
                        "Upload photos, write entries,\n" +
                        "and relive your favorite moments. \n" +
                        "Share your adventures with a vibrant\n" +
                        "community of fellow travelersaring, Enjoying Life",
                color = Color(0xffB3B3B3),
                fontSize = 14.sp,
                textAlign = Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(22.dp))

            DestiGoButton(
                onClick = { navController.navigate(route = SIGN_IN_SCREEN) },
                text = "Login",
                textColor = Color.Black,
                modifier = mainModifier,
                border = BorderStroke(1.dp, Color(0xff4F80FF))
            )

            VSpacer(height = SpacerHeights.large15)
            DestiGoButton(
                onClick = { navController.navigate(route = SIGN_UP_SCREEN) },
                text = "Sign Up",
                buttonColor = Color(0xff4F80FF),
                modifier = mainModifier,
                )

            VSpacer(height = SpacerHeights.extraLarge30)
            Text(
                text = "Continue as Guest",
                color = Color(0xffB3B3B3),
                style = ContentTextStyle14,
                modifier = Modifier.clickable {
                    viewModel.signInAnnonymously()
                    navController.navigate(route = HOME_SCREEN)
                }
            )
            SignInAnon()
        }
    }
}