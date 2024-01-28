package com.mobilebreakero.common_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@Composable
fun AuthButton(
    onClick: () -> Unit,
    buttonColor: Color? = Color.White,
    text: String,
    border: BorderStroke? = null,
    textColor: Color? = Color.White,
    modifier: Modifier,
    icon: Int? = null
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(buttonColor!!),
        border = border
    ) {
        icon?.let { painterResource(it) }
            ?.let { Icon(painter = it, contentDescription = "icon", tint = Color.Black) }

        Text(text = text, fontSize = 16.sp, color = textColor!!)
    }
}