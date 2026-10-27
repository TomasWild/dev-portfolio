package dev.tomaswild.portfolio.section

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.tomaswild.portfolio.adaptive.ScreenSize
import dev.tomaswild.portfolio.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private data class ExperienceItem(
    val role: StringResource,
    val companyName: StringResource,
    val timelapse: StringResource,
    val jobDescription: StringResource,
    val technologies: List<String>,
)

@Composable
fun Experience(
    screenSize: ScreenSize,
    modifier: Modifier = Modifier,
) {
    val isCompact = screenSize == ScreenSize.Compact
    var expandedIndex by rememberSaveable { mutableStateOf<Int?>(value = 0) }

    val experiences = listOf(
        ExperienceItem(
            role = Res.string.experience_epam_role,
            companyName = Res.string.experience_epam_company,
            timelapse = Res.string.experience_epam_period,
            jobDescription = Res.string.experience_epam_description,
            technologies = listOf("Kotlin", "Android", "Jetpack Compose"),
        ),
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        Text(
            text = stringResource(Res.string.experience_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(space = 12.dp),
        ) {
            experiences.forEachIndexed { index, item ->
                ExperienceAccordionItem(
                    item = item,
                    isCompact = isCompact,
                    expanded = expandedIndex == index,
                    onToggle = {
                        expandedIndex = if (expandedIndex == index) {
                            null
                        } else {
                            index
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun ExperienceAccordionItem(
    item: ExperienceItem,
    isCompact: Boolean,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderAlpha by animateFloatAsState(
        targetValue = if (expanded) {
            0.6F
        } else {
            0.2F
        },
        animationSpec = tween(durationMillis = 250),
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = borderAlpha),
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            AccordionHeader(
                item = item,
                isCompact = isCompact,
                expanded = expanded,
                onClick = onToggle,
                modifier = Modifier.clip(
                    shape = RoundedCornerShape(size = 20.dp),
                ),
            )

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = tween(durationMillis = 300)) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                    ),
                ),
                exit = shrinkVertically(animationSpec = tween(durationMillis = 250)) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 150,
                    ),
                ),
            ) {
                AccordionContent(item = item)
            }
        }
    }
}

@Composable
private fun AccordionHeader(
    item: ExperienceItem,
    isCompact: Boolean,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) {
            180F
        } else {
            0F
        },
        animationSpec = tween(durationMillis = 250),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                role = Role.Button,
                onClick = onClick,
            )
            .padding(all = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(weight = 1F),
            verticalArrangement = Arrangement.spacedBy(space = 2.dp),
        ) {
            if (isCompact) {
                Text(
                    text = stringResource(item.timelapse),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Text(
                text = stringResource(item.role),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(item.companyName),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        if (!isCompact) {
            Text(
                text = stringResource(item.timelapse),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Icon(
            painter = painterResource(resource = Res.drawable.ic_expand_more),
            contentDescription = null,
            modifier = Modifier
                .size(size = 24.dp)
                .rotate(degrees = chevronRotation),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun AccordionContent(
    item: ExperienceItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

        Text(
            text = stringResource(item.jobDescription),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Justify,
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            item.technologies.forEach {
                TechnologyItem(name = it)
            }
        }
    }
}

@Composable
private fun TechnologyItem(
    name: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(size = 100.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}