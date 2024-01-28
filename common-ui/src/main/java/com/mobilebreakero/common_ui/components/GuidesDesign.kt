package com.mobilebreakero.common_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.mobilebreakero.common_ui.design_system.TextStyles
import com.mobilebreakero.common_ui.design_system.Modifiers
import com.mobilebreakero.common_ui.design_system.Padding
import com.mobilebreakero.common_ui.design_system.SpacerHeights

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
            modifier = Modifiers.fillMaxSize,
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifiers
                .fillMaxSize
                .background(guideImageFilterColor)
        )
        Column(
            modifier = Modifiers
                .fillMaxSize
                .padding(Padding.medium),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = title,
                style = TextStyles.HeaderTextStyle
            )
            Text(
                text = description,
                style = TextStyles.MainTextStyle
            )
            VSpacer(height = SpacerHeights.medium)
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier
                    .padding(Padding.medium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonContainerColor,
                    contentColor = buttonContentColor
                )
            ) {
                Text(
                    text = buttonLabel,
                    style = TextStyles.ButtonTextStyle
                )
            }
        }
    }
}