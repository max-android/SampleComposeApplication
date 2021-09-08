package com.sample.ru.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

val lightThemeColors = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    secondaryVariant = SecondaryVariant,
    background = White,
    surface = White,
    error = LightError,
    onPrimary = Black,
    onSecondary = White,
    onBackground = Black,
    onSurface = Black,
    onError = White
)

val darkThemeColors = darkColors(
    primary = Black,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Black,
    surface = Black,
    error = DarkError,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White,
    onError = Black
)

@Composable
fun SampleComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        darkThemeColors
    } else {
        lightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = typography(),
        shapes = Shapes,
        content = content
    )
}