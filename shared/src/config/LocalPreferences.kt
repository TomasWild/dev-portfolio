package dev.tomaswild.portfolio.config

import kotlinx.browser.localStorage

object LocalPreferences {
    private const val KEY_LANGUAGE = "language"
    private const val KEY_THEME = "theme"

    var language: String?
        get() = read(key = KEY_LANGUAGE)
        set(value) {
            if (value != null) {
                write(key = KEY_LANGUAGE, value = value)
            }
        }

    var themeMode: ThemeMode
        get() = when (val stored = read(key = KEY_THEME)) {
            "true" -> ThemeMode.DARK
            "false" -> ThemeMode.LIGHT
            else -> ThemeMode.entries.firstOrNull { it.name == stored } ?: ThemeMode.SYSTEM
        }
        set(value) = write(key = KEY_THEME, value = value.name)

    private fun read(key: String): String? = try {
        localStorage.getItem(key = key)
    } catch (_: Throwable) {
        null
    }

    private fun write(key: String, value: String) {
        try {
            localStorage.setItem(key = key, value = value)
        } catch (_: Throwable) {
        }
    }
}