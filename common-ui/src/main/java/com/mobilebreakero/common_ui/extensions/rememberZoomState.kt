package com.mobilebreakero.common_ui.extensions

import androidx.annotation.FloatRange
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size

@Composable
fun rememberZoomState(
    @FloatRange(from = 1.0) maxScale: Float = 5f,
    contentSize: Size = Size.Zero,
    velocityDecay: DecayAnimationSpec<Float> = exponentialDecay(),
) = remember {
    ZoomState(maxScale, contentSize, velocityDecay)
}
