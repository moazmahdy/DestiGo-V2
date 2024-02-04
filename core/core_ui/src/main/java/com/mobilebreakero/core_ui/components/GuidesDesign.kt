package com.mobilebreakero.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.mobilebreakero.core_ui.design_system.Dimens
import com.mobilebreakero.core_ui.design_system.Dimens.medium10
import com.mobilebreakero.core_ui.design_system.Dimens.medium12
import com.mobilebreakero.core_ui.design_system.Modifiers
import com.mobilebreakero.core_ui.design_system.Modifiers.fillMaxSize
import com.mobilebreakero.core_ui.design_system.TextStyles.ContentTextStyle12
import com.mobilebreakero.core_ui.design_system.TextStyles.HeaderTextStyle36
import com.mobilebreakero.core_ui.design_system.TextStyles.MainTextStyle16

const val guideImageContentDescription = "guide card image"
val guideImageHeight = 500.dp
val guideImageFilterColor = Color.Black.copy(alpha = 0.3f)
val buttonContainerColor = Color.White
val buttonContentColor = Color.Black

@Composable
fun GuideCardDesign(
    image: Any,
    title: String,
    description: String,
    onClick: () -> Unit,
    buttonLabel: String
) {
    Box(
        modifier = Modifiers.fillMaxWidth.height(guideImageHeight)
    ) {
        SubcomposeAsyncImage(
            model = image,
            contentDescription = guideImageContentDescription,
            loading = { LoadingIndicator() },
            modifier = fillMaxSize,
            contentScale = FillBounds
        )
        Box(
            modifier = Modifiers
                .fillMaxSize
                .background(guideImageFilterColor)
        )
        Column(
            modifier = Modifiers
                .fillMaxSize
                .padding(medium10),
            verticalArrangement = Bottom
        ) {
            Text(
                text = title,
                style = HeaderTextStyle36
            )
            Text(
                text = description,
                style = MainTextStyle16
            )
            VSpacer(height = medium12)
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier
                    .padding(Dimens.medium10),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonContainerColor,
                    contentColor = buttonContentColor
                )
            ) {
                Text(
                    text = buttonLabel,
                    style = ContentTextStyle12
                )
            }
        }
    }
}