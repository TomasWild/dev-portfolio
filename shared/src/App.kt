package dev.tomaswild.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tomaswild.portfolio.adaptive.ScreenSize
import dev.tomaswild.portfolio.adaptive.currentScreenSize
import dev.tomaswild.portfolio.layout.Navbar
import dev.tomaswild.portfolio.localization.AppEnvironment
import dev.tomaswild.portfolio.localization.AppLanguage
import dev.tomaswild.portfolio.localization.customAppLocale
import dev.tomaswild.portfolio.resources.NotoSansSC_VariableFont_wght
import dev.tomaswild.portfolio.resources.Res
import dev.tomaswild.portfolio.section.About
import dev.tomaswild.portfolio.section.Experience
import dev.tomaswild.portfolio.section.Hero
import dev.tomaswild.portfolio.ui.theme.AppTheme
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
@DevelopmentEntryPoint
fun App() {
    val fontFamilyResolver = LocalFontFamilyResolver.current
    val font by preloadFont(resource = Res.font.NotoSansSC_VariableFont_wght)

    LaunchedEffect(key1 = font) {
        font?.let {
            fontFamilyResolver.preload(
                fontFamily = FontFamily(it),
            )
        }
    }

    val language = AppLanguage.entries.firstOrNull { it.code == customAppLocale }
        ?: AppLanguage.ENGLISH

    val systemDark = isSystemInDarkTheme()
    var isDarkTheme by rememberSaveable { mutableStateOf(value = systemDark) }

    AppTheme(
        darkTheme = isDarkTheme,
    ) {
        AppEnvironment {
            val screenSize = currentScreenSize()
            val horizontalPadding = when (screenSize) {
                ScreenSize.Compact -> 16.dp
                ScreenSize.Medium -> 32.dp
                ScreenSize.Expanded -> 48.dp
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 1100.dp)
                        .fillMaxSize()
                        .verticalScroll(state = rememberScrollState())
                        .padding(
                            start = horizontalPadding,
                            end = horizontalPadding,
                            top = when (screenSize) {
                                ScreenSize.Medium -> 24.dp + 120.dp + 24.dp
                                else -> 24.dp + 64.dp + 24.dp
                            },
                            bottom = 24.dp,
                        ),
                    verticalArrangement = Arrangement.spacedBy(space = 24.dp),
                ) {
                    Hero(
                        screenSize = screenSize,
                        onPrimaryClick = { /* TODO */ },
                        onSecondaryClick = { /* TODO */ },
                    )

                    About(screenSize = screenSize)

                    Experience(screenSize = screenSize)
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                            .safeContentPadding(),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        Navbar(
                            modifier = Modifier
                                .widthIn(max = 1100.dp)
                                .fillMaxWidth()
                                .padding(
                                    horizontal = horizontalPadding,
                                    vertical = 24.dp,
                                ),
                            screenSize = screenSize,
                            isDarkTheme = isDarkTheme,
                            language = language,
                            onHomeClick = { /* TODO */ },
                            onItemClick = { /* TODO */ },
                            onToggleTheme = { isDarkTheme = !isDarkTheme },
                            onLanguageChange = { customAppLocale = it.code },
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 24.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(MaterialTheme.colorScheme.background, Color.Transparent),
                                ),
                            ),
                    )
                }
            }
        }
    }
}
