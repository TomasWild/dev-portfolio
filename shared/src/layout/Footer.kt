package dev.tomaswild.portfolio.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.tomaswild.portfolio.adaptive.ScreenSize
import dev.tomaswild.portfolio.config.SocialMediaLinks
import dev.tomaswild.portfolio.navigation.NavSection
import dev.tomaswild.portfolio.resources.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock

private enum class SocialLink(
    val label: String,
    val url: String,
    val icon: DrawableResource,
) {
    GITHUB(label = "GitHub", url = SocialMediaLinks.GITHUB, icon = Res.drawable.github),
    LINKEDIN(label = "LinkedIn", url = SocialMediaLinks.LINKEDIN, icon = Res.drawable.linkedin),
    BLUESKY(label = "Bluesky", url = SocialMediaLinks.BLUESKY, icon = Res.drawable.bluesky),
}

@Composable
fun Footer(
    screenSize: ScreenSize,
    onNavigate: (NavSection) -> Unit,
    modifier: Modifier = Modifier,
    logo: String = "TW",
) {
    val currentYear = Clock.System
        .todayIn(TimeZone.currentSystemDefault())
        .year

    val copyright = stringResource(resource = Res.string.footer_copyright, currentYear)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        when (screenSize) {
            ScreenSize.COMPACT -> Column(
                verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                FooterSocialLinks()

                FooterNavigation(onNavigate = onNavigate)

                FooterInfo(
                    logo = logo,
                    copyright = copyright,
                    centered = true,
                )
            }

            ScreenSize.MEDIUM -> Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FooterInfo(
                    logo = logo,
                    copyright = copyright,
                    centered = false,
                    modifier = Modifier.weight(weight = 1F),
                )

                FooterNavigation(
                    onNavigate = onNavigate,
                    modifier = Modifier.weight(weight = 1F),
                )

                FooterSocialLinks()
            }

            else -> Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FooterInfo(
                    logo = logo,
                    copyright = copyright,
                    centered = false,
                )

                FooterNavigation(onNavigate = onNavigate)

                FooterSocialLinks()
            }
        }
    }
}

@Composable
private fun FooterInfo(
    logo: String,
    copyright: String,
    centered: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
        horizontalAlignment = if (centered) {
            Alignment.CenterHorizontally
        } else {
            Alignment.Start
        },
    ) {
        Text(
            text = logo,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = copyright,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = if (centered) {
                TextAlign.Center
            } else {
                TextAlign.Start
            },
        )
    }
}

@Composable
private fun FooterNavigation(
    onNavigate: (NavSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
    ) {
        NavSection.entries
            .filter { section -> section != NavSection.HOME }
            .forEach { section ->
                Text(
                    text = stringResource(resource = section.label),
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.small)
                        .clickable(role = Role.Button) {
                            onNavigate(section)
                        }
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        ),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
    }
}

@Composable
private fun FooterSocialLinks(
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
    ) {
        SocialLink.entries.forEach { link ->
            TooltipBox(
                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                    positioning = TooltipAnchorPosition.Below,
                ),
                tooltip = {
                    PlainTooltip {
                        Text(text = link.label)
                    }
                },
                state = rememberTooltipState(),
            ) {
                IconButton(
                    onClick = { uriHandler.openUri(uri = link.url) },
                ) {
                    Icon(
                        painter = painterResource(resource = link.icon),
                        contentDescription = link.label,
                        modifier = Modifier.size(size = 20.dp),
                    )
                }
            }
        }
    }
}