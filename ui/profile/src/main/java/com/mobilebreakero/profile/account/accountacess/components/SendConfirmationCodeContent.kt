package com.mobilebreakero.profile.account.accountacess.components

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.profile.account.accountacess.updatepassword.PasswordResetViewModel
import com.mobilebreakero.navigation_core.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.profile.R
import com.mobilebreakero.profile.component.AuthTextField

var ConfirmationCode = GenerateConfirmationCode()

@Composable
fun SendConfirmationCodeScreenContent(
    navController: NavController,
    sendConfirmationViewModel: PasswordResetViewModel = hiltViewModel()
) {

    val emailAddress = remember { mutableStateOf("") }
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
            text = "Reset Password",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Type your email to send\n" +
                    "a confirmation code for reset password. \n",
            color = Color(0xffB3B3B3),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        AuthTextField(text = emailAddress.value, onValueChange = {
            emailAddress.value = it
        }, label = "Email Address")

        Spacer(modifier = Modifier.height(33.dp))

        com.mobilebreakero.core_ui.components.DestiGoButton(
            onClick = {
                sendConfirmationViewModel.sendResetPasswordEmail(
                    emailAddress.value,
                    confirmationCode = ConfirmationCode
                )
            },
            buttonColor = Color(0xff4F80FF),
            text = "Send Confirmation Code", modifier = Modifier
                .shadow(elevation = 0.dp, shape = CircleShape)
                .width(270.dp)
                .height(50.dp)
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(33.dp))

        Text(
            text = "return to home",
            color = Color(0xffB3B3B3),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                navController.navigate(route = PROFILE_SCREEN)
            }
        )
    }

    SendEmailForResettingPassword(navController = navController)
}

fun CheckConfirmationCode(confirmationCode: Int, context: Context): Boolean {
    return if (confirmationCode == ConfirmationCode) {
        Toast.makeText(context, "Confirmation Successfully", Toast.LENGTH_SHORT).show()
        true
    } else {
        Toast.makeText(context, "Wrong Code", Toast.LENGTH_SHORT).show()
        false
    }
}

fun GenerateConfirmationCode(): Int {
    return (100000..999999).random()
}