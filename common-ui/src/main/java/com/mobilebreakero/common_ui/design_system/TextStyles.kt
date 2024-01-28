package com.mobilebreakero.common_ui.design_system

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em

val lineHeight = 1.2.em
val roboto = Fonts.Roboto
val arialRegular = Fonts.ArialRegular

object TextStyles {

    val HeaderTextStyle = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize.xxlarge,
        lineHeight = lineHeight
    )

    val MainTextStyle = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = fontSize.medium,
    )

    val SideHeaderTextStyle = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontSize = fontSize.large,
    )

    val ButtonTextStyle = TextStyle(
        color = Color.White,
        fontFamily = arialRegular,
        fontSize = fontSize.small,
    )

    val ThirdHeaderTextStyle = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontSize = fontSize.xlarge,
    )
}