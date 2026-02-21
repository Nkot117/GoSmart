package com.nkot117.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Primary500,
    onPrimary = Color.White,
    secondary = Secondary400,
    background = Color.White,
    surface = Color.White,
    onBackground = TextMain,
    onSurface = TextMain,
    outline = BorderLine
)

@Composable
fun SmartGoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        content = content
    )
}
