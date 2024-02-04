package com.mobilebreakero.profile.account.accountacess

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilebreakero.navigation_core.NavigationRoutes.CHOOSE_NEW_EMAIL
import com.mobilebreakero.navigation_core.NavigationRoutes.CHOOSE_NEW_USERNAME
import com.mobilebreakero.navigation_core.NavigationRoutes.SEND_CONFIRMATION_CODE
import com.mobilebreakero.profile.R
import com.mobilebreakero.profile.account.accountacess.deleteyouremail.DeleteAccountConfirmation
import com.mobilebreakero.profile.account.settings.SettingsCard

@Composable
fun AccountAccessSettingsContent(navController: NavController) {

    val isDeleteAccountConfirmation = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .height(400.dp)
                .width(390.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp))
                .align(Alignment.Center)
                .background(Color(0xFFF8FAFF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SettingsCard(icon = R.drawable.account, text = "change username", onClick = {
                navController.navigate(CHOOSE_NEW_USERNAME)
            })
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(
                icon = R.drawable.password,
                text = "update password",
                onClick = { navController.navigate(SEND_CONFIRMATION_CODE) })
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(
                icon = R.drawable.account,
                text = "update email",
                onClick = { navController.navigate(CHOOSE_NEW_EMAIL) })
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.delete, text = "delete your email", onClick = {
                isDeleteAccountConfirmation.value = true
            })
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    if (isDeleteAccountConfirmation.value) {
        DeleteAccountConfirmation(email = "", showDialogs = isDeleteAccountConfirmation.value)
    }
}