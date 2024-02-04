package com.mobilebreakero.core_ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobilebreakero.core_ui.R


@Composable
fun AnimateIcon(modifier: Modifier = Modifier, color: Color) {
    val iconOffset = remember { Animatable(0f) }
    val animationIterations = 200

    LaunchedEffect(Unit) {
        repeat(animationIterations) {
            iconOffset.animateTo(0f, animationSpec = tween(1000))
            iconOffset.animateTo(5f, animationSpec = tween(1000))
        }
    }

    Icon(
        painter = painterResource(id = R.drawable.doublearrow),
        tint = color,
        contentDescription = "arrow",
        modifier = modifier
            .graphicsLayer {
                translationX = iconOffset.value.dp.toPx()
            }
    )
}
