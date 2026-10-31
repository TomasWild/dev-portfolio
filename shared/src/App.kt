package dev.tomaswild.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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
import dev.tomaswild.portfolio.config.LocalPreferences
import dev.tomaswild.portfolio.config.ThemeMode
import dev.tomaswild.portfolio.config.resolveIsDark
import dev.tomaswild.portfolio.layout.Footer
import dev.tomaswild.portfolio.layout.Navbar
import dev.tomaswild.portfolio.localization.AppEnvironment
import dev.tomaswild.portfolio.localization.AppLanguage
import dev.tomaswild.portfolio.localization.customAppLocale
import dev.tomaswild.portfolio.localization.setAppLanguage
import dev.tomaswild.portfolio.navigation.NavSection
import dev.tomaswild.portfolio.navigation.listIndex
import dev.tomaswild.portfolio.resources.NotoSansSC_VariableFont_wght
import dev.tomaswild.portfolio.resources.Res
import dev.tomaswild.portfolio.section.*
import dev.tomaswild.portfolio.ui.theme.AppTheme
import kotlinx.coroutines.launch
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

    var themeMode by remember { mutableStateOf(value = LocalPreferences.themeMode) }
    val isDarkTheme = themeMode.resolveIsDark()

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

            val listState = rememberLazyListState()
            val scope = rememberCoroutineScope()

            val scrollToSection: (NavSection) -> Unit = { section ->
                section.listIndex().let { index ->
                    scope.launch { listState.animateScrollToItem(index = index) }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                LazyColumn(
                    modifier = Modifier
                        .widthIn(max = 1100.dp)
                        .fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(
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
                    item {
                        Hero(
                            screenSize = screenSize,
                            onPrimaryClick = { scrollToSection(NavSection.CONTACT) },
                            onSecondaryClick = { /* TODO */ },
                        )
                    }

                    item {
                        About(screenSize = screenSize)
                    }

                    item {
                        Experience(screenSize = screenSize)
                    }

                    item {
                        Projects(screenSize = screenSize)
                    }

                    item {
                        Contact(
                            screenSize = screenSize,
                            onSendClick = { /* TODO */ },
                        )
                    }

                    item {
                        Footer(
                            screenSize = screenSize,
                            onNavigate = scrollToSection,
                        )
                    }
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
                            onItemClick = scrollToSection,
                            onToggleTheme = {
                                themeMode = if (isDarkTheme) {
                                    ThemeMode.LIGHT
                                } else {
                                    ThemeMode.DARK
                                }

                                LocalPreferences.themeMode = themeMode
                            },
                            onLanguageChange = { setAppLanguage(it) },
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
