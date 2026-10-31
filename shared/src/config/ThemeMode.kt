package dev.tomaswild.portfolio.config

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}

@Composable
fun ThemeMode.resolveIsDark(): Boolean = when (this) {
    ThemeMode.LIGHT -> false
    ThemeMode.DARK -> true
    ThemeMode.SYSTEM -> isSystemInDarkTheme()
}