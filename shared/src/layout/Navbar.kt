package dev.tomaswild.portfolio.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.tomaswild.portfolio.adaptive.ScreenSize
import dev.tomaswild.portfolio.localization.AppLanguage
import dev.tomaswild.portfolio.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

enum class NavSection(
    val label: StringResource,
) {
    About(label = Res.string.navigation_about),
    Experience(label = Res.string.navigation_experience),
    Projects(label = Res.string.navigation_projects),
    Contact(label = Res.string.navigation_contact),
}

@Composable
fun Navbar(
    screenSize: ScreenSize,
    isDarkTheme: Boolean,
    language: AppLanguage,
    onHomeClick: () -> Unit,
    onItemClick: (NavSection) -> Unit,
    onToggleTheme: () -> Unit,
    onLanguageChange: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "TW",
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(
                insets = WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
            ),
        shape = RoundedCornerShape(
            size = if (screenSize == ScreenSize.Medium) {
                32.dp
            } else {
                100.dp
            }
        ),
        color = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 3.dp,
        shadowElevation = 6.dp,
    ) {
        when (screenSize) {
            ScreenSize.Medium -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 64.dp)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        LogoWithTooltip(
                            title = title,
                            onClick = onHomeClick,
                        )

                        Row(
                            modifier = Modifier.align(alignment = Alignment.CenterEnd),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
                        ) {
                            NavbarControls(
                                screenSize = screenSize,
                                isDarkTheme = isDarkTheme,
                                language = language,
                                onItemClick = onItemClick,
                                onToggleTheme = onToggleTheme,
                                onLanguageChange = onLanguageChange,
                            )
                        }
                    }

                    ExpandedNavActions(
                        onItemClick = onItemClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 4.dp,
                                bottom = 12.dp,
                            ),
                    )
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 64.dp)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    LogoWithTooltip(
                        title = title,
                        onClick = onHomeClick,
                    )

                    if (screenSize != ScreenSize.Compact) {
                        ExpandedNavActions(
                            onItemClick = onItemClick,
                            modifier = Modifier.align(alignment = Alignment.Center),
                        )
                    }

                    Row(
                        modifier = Modifier.align(alignment = Alignment.CenterEnd),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
                    ) {
                        NavbarControls(
                            screenSize = screenSize,
                            isDarkTheme = isDarkTheme,
                            language = language,
                            onItemClick = onItemClick,
                            onToggleTheme = onToggleTheme,
                            onLanguageChange = onLanguageChange,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(value = false) }

    LaunchedEffect(key1 = Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(durationMillis = 600)) + slideInHorizontally(
            animationSpec = tween(durationMillis = 600),
            initialOffsetX = { fullWidth -> -fullWidth / 2 },
        ),
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val isPressed by interactionSource.collectIsPressedAsState()

        val scale by animateFloatAsState(
            targetValue = when {
                isPressed -> 0.94F
                isHovered -> 1.06F
                else -> 1F
            },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium,
            ),
        )

        val containerColor by animateColorAsState(
            targetValue = if (isHovered) {
                MaterialTheme.colorScheme.secondary
            } else {
                Color.Transparent
            },
            animationSpec = tween(durationMillis = 250),
        )

        val contentColor by animateColorAsState(
            targetValue = if (isHovered) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            animationSpec = tween(durationMillis = 250),
        )

        Button(
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun LogoWithTooltip(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Below,
        ),
        tooltip = {
            PlainTooltip {
                Text(
                    text = stringResource(resource = Res.string.navigation_home_tooltip),
                )
            }
        },
        state = rememberTooltipState(),
    ) {
        HomeButton(
            title = title,
            onClick = { onClick() },
        )
    }
}

@Composable
private fun ThemeToggle(
    isDarkTheme: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Below,
        ),
        tooltip = {
            PlainTooltip {
                Text(
                    text = stringResource(resource = Res.string.navigation_theme_tooltip),
                )
            }
        },
        state = rememberTooltipState(),
    ) {
        IconButton(
            onClick = onToggle,
        ) {
            Icon(
                painter = painterResource(
                    resource = if (isDarkTheme) {
                        Res.drawable.ic_light_mode
                    } else {
                        Res.drawable.ic_dark_mode
                    }
                ),
                contentDescription = if (isDarkTheme) {
                    stringResource(resource = Res.string.theme_switch_to_light)
                } else {
                    stringResource(resource = Res.string.theme_switch_to_dark)
                },
            )
        }
    }
}

@Composable
private fun LanguageSwitcher(
    selected: AppLanguage,
    onSelect: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(value = false) }

    Box(
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TooltipBox(
                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                    positioning = TooltipAnchorPosition.Below,
                ),
                tooltip = {
                    PlainTooltip {
                        Text(
                            text = stringResource(resource = Res.string.navigation_language_tooltip),
                        )
                    }
                },
                state = rememberTooltipState(),
            ) {
                IconButton(
                    onClick = { expanded = true },
                ) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_language),
                        contentDescription = stringResource(resource = Res.string.language_change),
                    )
                }
            }

            Text(text = selected.displayName)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            AppLanguage.entries.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = language.displayName,
                            fontWeight = if (language == selected) {
                                FontWeight.Bold
                            } else {
                                null
                            },
                        )
                    },
                    onClick = {
                        expanded = false
                        onSelect(language)
                    },
                )
            }
        }
    }
}

@Composable
private fun NavbarControls(
    screenSize: ScreenSize,
    isDarkTheme: Boolean,
    language: AppLanguage,
    onItemClick: (NavSection) -> Unit,
    onToggleTheme: () -> Unit,
    onLanguageChange: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LanguageSwitcher(
            selected = language,
            onSelect = onLanguageChange,
        )

        Spacer(modifier = Modifier.width(width = 8.dp))

        ThemeToggle(
            isDarkTheme = isDarkTheme,
            onToggle = onToggleTheme,
        )

        if (screenSize == ScreenSize.Compact) {
            CompactNavActions(onItemClick = onItemClick)
        }
    }
}

@Composable
private fun ExpandedNavActions(
    onItemClick: (NavSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NavSection.entries.forEach { section ->
            TextButton(
                onClick = { onItemClick(section) },
            ) {
                Text(
                    text = stringResource(resource = section.label),
                )
            }
        }
    }
}

@Composable
private fun CompactNavActions(
    onItemClick: (NavSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(value = false) }

    Box(
        modifier = modifier,
    ) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                positioning = TooltipAnchorPosition.Below,
            ),
            tooltip = {
                PlainTooltip {
                    Text(
                        text = stringResource(resource = Res.string.navigation_menu_tooltip),
                    )
                }
            },
            state = rememberTooltipState(),
        ) {
            IconButton(
                onClick = { expanded = !expanded },
            ) {
                Icon(
                    painter = painterResource(
                        resource = if (expanded) {
                            Res.drawable.ic_close_menu
                        } else {
                            Res.drawable.ic_menu
                        },
                    ),
                    contentDescription = if (expanded) {
                        stringResource(resource = Res.string.menu_close)
                    } else {
                        stringResource(resource = Res.string.menu_open)
                    },
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            NavSection.entries.forEach { section ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(resource = section.label),
                        )
                    },
                    onClick = {
                        expanded = false
                        onItemClick(section)
                    },
                )
            }
        }
    }
}