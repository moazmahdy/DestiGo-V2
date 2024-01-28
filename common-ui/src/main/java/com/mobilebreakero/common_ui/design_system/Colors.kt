package com.mobilebreakero.common_ui.design_system

import androidx.compose.ui.graphics.Color

val black = Color.Black
val white = Color.White

val lightBlack = Color.Black.copy(alpha = 0.3f)
val lightGray = Color.Gray.copy(alpha = 0.3f)
val darkGray = Color.Gray.copy(alpha = 0.7f)
val darkBlack = Color.Black.copy(alpha = 0.6f)

val blueColor = Color(0xff4F80FF)
val lightBlueColor = Color(0xFF91B0FF)
val lightBlueColor2 = Color(0xFFAFC9FF)

object Colors {

    val mainTextColor = black
    val secondaryTextColor = lightBlack
    val blueTextColor = blueColor
    val lightBlueTextColor = lightBlueColor2

    val buttonContainerColor = blueColor
    val buttonContentColor = white

    val imageFilterColor = lightBlack
    val darkerImageFilterColor = darkBlack

    val inputFieldContainerColor = lightGray
    val inputFieldTextColor = black
    val inputFieldHintTextColor = lightBlack

    val blueBorderColor = blueColor
    val blackBorderColor = darkGray

    val secondaryContainer = lightBlueColor
}