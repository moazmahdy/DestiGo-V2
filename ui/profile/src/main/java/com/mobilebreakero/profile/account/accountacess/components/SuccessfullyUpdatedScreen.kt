package com.mobilebreakero.profile.account.accountacess.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.navigation_core.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.profile.R

@Composable
fun PasswordUpdatedSuccessfullyScreen(navController: NavController) {
    PasswordUpdatedSuccessfullyScreenContent(
        navController = navController,
        passwordOrEmail = "Your password has been updated successfully"
    )
}

@Composable
fun PasswordUpdatedSuccessfullyScreenContent(
    navController: NavController,
    passwordOrEmail: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.correct),
            contentDescription = "",
            modifier = Modifier
                .height(155.dp)
                .width(155.dp)
        )
        Text(text = "Congrats", color = Color.Black, fontSize = 26.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = passwordOrEmail,
            color = Color.Black.copy(alpha = 0.4f),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        com.mobilebreakero.core_ui.components.DestiGoButton(
            onClick = { navController.navigate(PROFILE_SCREEN) },
            text = "Return to Profile",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .width(290.dp)
                .height(45.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp, vertical = 2.dp),
        )
    }
}