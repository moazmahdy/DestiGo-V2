package com.mobilebreakero.core_ui.design_system

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em

val lineHeight = 1.2.em
val roboto = Fonts.Roboto
val arialRegular = Fonts.ArialRegular

object TextStyles {

    val HeaderTextStyle32 = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize.extraLarge32,
        lineHeight = lineHeight
    )

    val HeaderTextStyle34 = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize.extraLarge34,
        lineHeight = lineHeight
    )

    val HeaderTextStyle36 = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize.extraLarge36,
        lineHeight = lineHeight
    )

    val MainTextStyle16 = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = fontSize.medium16,
    )

    val HeaderTextStyle20 = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontSize = fontSize.large20,
    )

    val ContentTextStyle12 = TextStyle(
        color = Color.White,
        fontFamily = arialRegular,
        fontSize = fontSize.small12,
    )


    val ContentTextStyle14 = TextStyle(
        color = Color.White,
        fontFamily = arialRegular,
        fontSize = fontSize.small14,
    )

    val HeaderTextStyle24 = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontSize = fontSize.large24,
    )

    val HeaderTextStyle14 = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontSize = fontSize.large24,
        fontWeight = FontWeight.Bold
    )

    val HeaderTextStyle28 = TextStyle(
        color = Color.White,
        fontFamily = roboto,
        fontSize = fontSize.large28,
    )

}