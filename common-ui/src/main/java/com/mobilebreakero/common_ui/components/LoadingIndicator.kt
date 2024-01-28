package com.mobilebreakero.common_ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
) {
    Row(
    ) {
        Text("Loading...")
        Spacer(modifier = Modifier.width(3.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = Color(0xFF6200EE)
        )
    }

}

@Composable
fun LinearIndicator(indicatorProgress: Float) {
    var progress by remember { mutableStateOf(0f) }
    val progressAnimDuration = 1500
    val progressAnimation by animateFloatAsState(
        targetValue = indicatorProgress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
        label = ""
    )
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        progress = progressAnimation
    )
    LaunchedEffect(indicatorProgress) {
        progress = indicatorProgress
    }
}