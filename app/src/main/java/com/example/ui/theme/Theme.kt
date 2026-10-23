package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = CyanAccent,
    onPrimary = ObsidianBg,
    primaryContainer = NavySurface,
    onPrimaryContainer = CyanAccent,
    secondary = EmeraldSuccess,
    onSecondary = ObsidianBg,
    error = RoseDanger,
    onError = ObsidianBg,
    background = ObsidianBg,
    onBackground = TextPrimary,
    surface = NavySurface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceDarkVariant,
    onSurfaceVariant = TextSecondary,
    outline = CardBorder
)

@Composable
fun AISCommandCenterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
