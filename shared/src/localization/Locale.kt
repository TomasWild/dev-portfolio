package dev.tomaswild.portfolio.localization

import androidx.compose.runtime.*
import androidx.compose.ui.text.intl.Locale
import dev.tomaswild.portfolio.config.LocalPreferences
import kotlinx.browser.window

enum class AppLanguage(
    val code: String,
    val displayName: String,
) {
    ENGLISH(code = "en", displayName = "English"),
    SPANISH(code = "es", displayName = "Español"),
    CHINESE(code = "zh", displayName = "中文 - 简体"),
}

var customAppLocale by mutableStateOf<String?>(value = initialLanguage())

object AppLocale {
    private val localAppLocale = staticCompositionLocalOf { Locale.current }

    val current: String
        @Composable get() = localAppLocale.current.toString()

    @Composable
    infix fun provides(value: String?): ProvidedValue<*> {
        updateCustomLocale(value?.replace(oldChar = '_', newChar = '-'))

        return localAppLocale.provides(value = Locale.current)
    }
}

@Composable
fun AppEnvironment(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(value = AppLocale provides customAppLocale) {
        key(customAppLocale) {
            content()
        }
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
private fun updateCustomLocale(value: String?) {
    js(
        code = """
        if (window.__customLocale !== value) {
            window.__customLocale = value;
            window.dispatchEvent(new Event("languagechange"));
        }
        """
    )
}

fun setAppLanguage(language: AppLanguage) {
    customAppLocale = language.code
    LocalPreferences.language = language.code
}

private fun initialLanguage(): String {
    val browser = window.navigator.language.substringBefore(delimiter = '-')

    return AppLanguage.entries.firstOrNull { it.code == LocalPreferences.language }?.code
        ?: AppLanguage.entries.firstOrNull { it.code == browser }?.code
        ?: AppLanguage.ENGLISH.code
}