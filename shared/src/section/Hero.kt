package dev.tomaswild.portfolio.section

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tomaswild.portfolio.adaptive.ScreenSize
import dev.tomaswild.portfolio.config.SocialMediaLinks
import dev.tomaswild.portfolio.resources.*
import kotlinx.coroutines.isActive
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private data class Skill(
    val name: String,
    val icon: DrawableResource,
)

@Composable
fun Hero(
    screenSize: ScreenSize,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val skills = listOf(
        Skill(name = "Java", icon = Res.drawable.java),
        Skill(name = "Kotlin", icon = Res.drawable.kotlin),
        Skill(name = "Spring Boot", icon = Res.drawable.spring_boot),
        Skill(name = "Ktor", icon = Res.drawable.ktor),
        Skill(name = "Android", icon = Res.drawable.android),
        Skill(name = "Compose Multiplatform", icon = Res.drawable.compose_multiplatform),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (screenSize) {
            ScreenSize.Compact -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 32.dp),
            ) {
                ProfileImage(size = 160.dp)

                HeroContent(
                    nameStyle = MaterialTheme.typography.displaySmall,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    textAlign = TextAlign.Center,
                    onPrimaryClick = onPrimaryClick,
                    onSecondaryClick = onSecondaryClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            ScreenSize.Medium -> Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HeroContent(
                    nameStyle = MaterialTheme.typography.displayMedium,
                    horizontalAlignment = Alignment.Start,
                    textAlign = TextAlign.Start,
                    onPrimaryClick = onPrimaryClick,
                    onSecondaryClick = onSecondaryClick,
                    modifier = Modifier.weight(weight = 1F),
                )

                ProfileImage(size = 240.dp)
            }

            ScreenSize.Expanded -> Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 72.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 64.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HeroContent(
                    nameStyle = MaterialTheme.typography.displayLarge,
                    horizontalAlignment = Alignment.Start,
                    textAlign = TextAlign.Justify,
                    onPrimaryClick = onPrimaryClick,
                    onSecondaryClick = onSecondaryClick,
                    modifier = Modifier.weight(weight = 1F),
                )

                ProfileImage(size = 320.dp)
            }
        }

        SkillsSection(
            skills = skills,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun HeroContent(
    nameStyle: TextStyle,
    horizontalAlignment: Alignment.Horizontal,
    textAlign: TextAlign,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
    ) {
        Text(
            text = stringResource(resource = Res.string.hero_greeting),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = stringResource(resource = Res.string.hero_name),
            style = nameStyle,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = textAlign,
        )

        Text(
            text = stringResource(resource = Res.string.hero_career),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = textAlign,
        )

        Text(
            text = stringResource(resource = Res.string.hero_introduction),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = textAlign,
            modifier = Modifier.widthIn(max = 520.dp),
        )

        Spacer(modifier = Modifier.height(height = 8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = if (horizontalAlignment == Alignment.CenterHorizontally) {
                    Alignment.CenterHorizontally
                } else {
                    Alignment.Start
                },
            ),
            verticalArrangement = Arrangement.spacedBy(space = 12.dp),
        ) {
            Button(
                onClick = onPrimaryClick,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = stringResource(resource = Res.string.hero_primary_action))

                    Spacer(modifier = Modifier.width(width = 8.dp))

                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_arrow_forward),
                        contentDescription = stringResource(resource = Res.string.hero_primary_action),
                    )
                }
            }

            OutlinedButton(
                onClick = onSecondaryClick,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                ),
                enabled = false,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_download),
                        contentDescription = stringResource(resource = Res.string.hero_secondary_action),
                    )

                    Spacer(modifier = Modifier.width(width = 8.dp))

                    Text(text = stringResource(resource = Res.string.hero_secondary_action))
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = if (horizontalAlignment == Alignment.CenterHorizontally) {
                    Alignment.CenterHorizontally
                } else {
                    Alignment.Start
                },
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val uriHandler = LocalUriHandler.current

            Text(
                text = stringResource(resource = Res.string.hero_social_links_label),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
            )

            SocialMediaSection(
                socialMediaName = "GitHub",
                onClick = { uriHandler.openUri(SocialMediaLinks.GITHUB) },
                painter = painterResource(resource = Res.drawable.github),
            )

            SocialMediaSection(
                socialMediaName = "LinkedIn",
                onClick = { uriHandler.openUri(SocialMediaLinks.LINKEDIN) },
                painter = painterResource(resource = Res.drawable.linkedin),
            )

            SocialMediaSection(
                socialMediaName = "Bluesky",
                onClick = { uriHandler.openUri(SocialMediaLinks.BLUESKY) },
                painter = painterResource(resource = Res.drawable.bluesky),
            )
        }
    }
}

@Composable
private fun ProfileImage(
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size = size)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4F),
                shape = CircleShape,
            )
            .padding(all = 10.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.male_avatar_placeholder),
            contentDescription = null,
            modifier = Modifier.clip(shape = CircleShape),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun SocialMediaSection(
    socialMediaName: String,
    onClick: () -> Unit,
    painter: Painter,
    modifier: Modifier = Modifier,
) {
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Below,
        ),
        tooltip = {
            PlainTooltip {
                Text(text = socialMediaName)
            }
        },
        state = rememberTooltipState(),
    ) {
        IconButton(
            onClick = { onClick() },
        ) {
            Icon(
                painter = painter,
                contentDescription = socialMediaName,
                modifier = Modifier.size(size = 24.dp),
            )
        }
    }
}

@Composable
private fun SkillsSection(
    skills: List<Skill>,
    modifier: Modifier = Modifier,
) {
    if (skills.isEmpty()) {
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(resource = Res.string.hero_skills_label),
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        SkillsRow(skills = skills)
    }
}

@Composable
private fun SkillsRow(
    skills: List<Skill>,
    modifier: Modifier = Modifier,
    speedDpPerSecond: Dp = 60.dp,
) {
    val density = LocalDensity.current
    val scrollState = rememberScrollState()
    var setWidthPx by remember { mutableStateOf(value = 0) }

    val gapPx = with(receiver = density) {
        24.dp.roundToPx()
    }

    val speedPxPerSecond = with(receiver = density) {
        speedDpPerSecond.toPx()
    }

    LaunchedEffect(
        key1 = setWidthPx,
        key2 = speedPxPerSecond,
    ) {
        if (setWidthPx <= 0) {
            return@LaunchedEffect
        }

        var last = withFrameNanos { it }
        var offset = 0F

        while (isActive) {
            val now = withFrameNanos { it }

            offset = (offset + speedPxPerSecond * (now - last) / 1_000_000_000F) % setWidthPx
            last = now
            scrollState.scrollTo(value = offset.toInt())
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.horizontalGradient(
                        0F to Color.Transparent,
                        0.1F to Color.Black,
                        0.9F to Color.Black,
                        1F to Color.Transparent,
                    ),
                    blendMode = BlendMode.DstIn,
                )
            }
            .horizontalScroll(
                state = scrollState,
                enabled = false,
            ),
        horizontalArrangement = Arrangement.spacedBy(space = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(times = 4) { copy ->
            Row(
                modifier = if (copy == 0) {
                    Modifier.onSizeChanged { setWidthPx = it.width + gapPx }
                } else {
                    Modifier
                },
                horizontalArrangement = Arrangement.spacedBy(space = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                skills.forEach {
                    SkillItem(skill = it)
                }
            }
        }
    }
}

@Composable
private fun SkillItem(
    skill: Skill,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = CircleShape,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(resource = skill.icon),
            contentDescription = null,
            modifier = Modifier.size(size = 24.dp),
        )

        Text(
            text = skill.name,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
        )
    }
}