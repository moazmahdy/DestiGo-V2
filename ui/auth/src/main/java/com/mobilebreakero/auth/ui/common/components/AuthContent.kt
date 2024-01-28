package com.mobilebreakero.auth.ui.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobilebreakero.auth.R


@Composable
fun AuthContent() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(100.dp)
                .width(120.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}