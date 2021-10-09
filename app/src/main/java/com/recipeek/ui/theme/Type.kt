package com.recipeek.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.recipeek.R

val Fellix = FontFamily(
    Font(R.font.fellix_light, FontWeight.Light),
    Font(R.font.fellix_regular, FontWeight.Normal),
    Font(R.font.fellix_medium, FontWeight.Medium),
    Font(R.font.fellix_semibold, FontWeight.SemiBold),
    Font(R.font.fellix_bold, FontWeight.Bold),
)

private val defaultTextStyle = TextStyle(
    fontFamily = Fellix,
    fontWeight = FontWeight.Normal,
    letterSpacing = 0.sp
)

private val UNDEFINED_SIZE = 4.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = defaultTextStyle.copy(fontSize = 32.sp),
    h2 = defaultTextStyle.copy(fontSize = 22.sp),
    h3 = defaultTextStyle.copy(fontSize = 20.sp),
    h4 = defaultTextStyle.copy(fontSize = 18.sp, fontWeight = FontWeight.Medium),
    h5 = defaultTextStyle.copy(fontSize = UNDEFINED_SIZE),
    h6 = defaultTextStyle.copy(fontSize = UNDEFINED_SIZE),
    subtitle1 = defaultTextStyle.copy(fontSize = UNDEFINED_SIZE),
    subtitle2 = defaultTextStyle.copy(fontSize = UNDEFINED_SIZE),
    body1 = defaultTextStyle.copy(fontSize = 16.sp),
    body2 = defaultTextStyle.copy(fontSize = UNDEFINED_SIZE),
    button = defaultTextStyle.copy(fontSize = 15.sp),
    caption = defaultTextStyle.copy(fontSize = 14.sp),
    overline = defaultTextStyle.copy(fontSize = 12.sp)
)