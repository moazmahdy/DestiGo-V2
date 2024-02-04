package com.mobilebreakero.profile.account.accountacess.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.mobilebreakero.profile.R


@Composable
fun ConfirmTheConfirmationCodeScreenContent(
    navController: NavController,
    cancelNavDirection: String,
    text: String,
    submitNavDirection: String
) {

    var confirmationCode by remember { mutableStateOf("") }

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
            text = "Confirmation Code",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "We sent a confirmation code \n" +
                    "to your email",
            color = Color(0xffB3B3B3),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        ConfirmationCodeTextField(
            otpText = confirmationCode,
            onOtpTextChange = { value, otpInputFilled ->
                confirmationCode = value
            }
        )

        Spacer(modifier = Modifier.height(33.dp))

        val context = LocalContext.current
        com.mobilebreakero.core_ui.components.DestiGoButton(
            onClick = {
                if (CheckConfirmationCode(confirmationCode.toInt(), context)
                ) {
                    navController.navigate(route = submitNavDirection)
                }
            },
            buttonColor = Color(0xff4F80FF),
            text = "Submit", modifier = Modifier
                .shadow(elevation = 0.dp, shape = CircleShape)
                .width(270.dp)
                .height(50.dp)
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(33.dp))

        Text(
            text = text,
            color = Color(0xffB3B3B3),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                navController.navigate(route = cancelNavDirection)
            }
        )
    }

}