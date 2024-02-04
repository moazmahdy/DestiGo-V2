package com.mobilebreakero.ui.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.mobilebreakero.core_ui.components.VSpacer
import com.mobilebreakero.core_ui.design_system.Dimens.large20
import com.mobilebreakero.core_ui.design_system.MainHeight.extraLargeHeight100
import com.mobilebreakero.core_ui.design_system.MainWidth.mediumWidth120
import com.mobilebreakero.ui.R


@Composable
fun AuthContent() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(extraLargeHeight100)
                .width(mediumWidth120)
        )
        VSpacer(height = large20)
    }
}