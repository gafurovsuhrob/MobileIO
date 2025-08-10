package com.seros.mobileio.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryBlue,
    tertiary = TertiaryBlue,
    background = LightGrayBackground,
    surface = LightGrayBackground,
    onPrimary = TextColorFields,
    onSecondary = TextColorSecondary,
    onTertiary = TextColor,
    onBackground = TextColor,
    onSurface = TextColor,
    onTertiaryContainer = Color.White
)

@Composable
fun MobileIOTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
