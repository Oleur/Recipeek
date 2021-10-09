package com.recipeek.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ColorSet(val primary: Color, val secondary: Color) {
}

@Composable
fun alert(): ColorSet = ColorSet(Color.Red, Color.Yellow)

@Composable
fun info(): ColorSet = ColorSet(Color.Blue, Color.Green)

@Composable
fun default(): ColorSet = ColorSet(AppColorsTheme.colors.bgButtonPrimaryOn, AppColorsTheme.colors.bgButtonPrimaryDisabled)
