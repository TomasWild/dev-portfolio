package dev.tomaswild.portfolio.section

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.tomaswild.portfolio.adaptive.ScreenSize
import dev.tomaswild.portfolio.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

private data class Highlight(
    val value: StringResource,
    val label: StringResource,
)

@Composable
fun About(
    screenSize: ScreenSize,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = when (screenSize) {
                    ScreenSize.Compact -> 24.dp
                    ScreenSize.Medium -> 40.dp
                    ScreenSize.Expanded -> 56.dp
                },
            ),
        verticalArrangement = Arrangement.spacedBy(space = 32.dp),
    ) {
        SectionHeader(
            eyebrow = stringResource(resource = Res.string.about_eyebrow),
            title = stringResource(resource = Res.string.about_title),
        )

        when (screenSize) {
            ScreenSize.Compact -> Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = 24.dp),
            ) {
                AboutStory(modifier = Modifier.fillMaxWidth())

                AboutHighlights(modifier = Modifier.fillMaxWidth())
            }

            else -> Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = if (screenSize == ScreenSize.Expanded) {
                        64.dp
                    } else {
                        40.dp
                    },
                ),
                verticalAlignment = Alignment.Top,
            ) {
                AboutStory(modifier = Modifier.weight(weight = 3F))

                AboutHighlights(modifier = Modifier.weight(weight = 2F))
            }
        }
    }
}

@Composable
private fun SectionHeader(
    eyebrow: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        Text(
            text = eyebrow,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun AboutStory(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        Text(
            text = stringResource(resource = Res.string.about_paragraph_1),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Justify,
        )

        Text(
            text = stringResource(resource = Res.string.about_paragraph_2),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Justify,
        )

        Text(
            text = stringResource(resource = Res.string.about_paragraph_3),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
private fun AboutHighlights(
    modifier: Modifier = Modifier,
) {
    val highlights = listOf(
        Highlight(value = Res.string.about_experience_value, label = Res.string.about_experience_label),
        Highlight(value = Res.string.about_specialization_value, label = Res.string.about_specialization_label),
        Highlight(value = Res.string.about_languages_value, label = Res.string.about_languages_label),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
    ) {
        highlights.forEach { highlight ->
            HighlightCard(
                value = stringResource(resource = highlight.value),
                label = stringResource(resource = highlight.label),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun HighlightCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(size = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(space = 4.dp),
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Justify,
            )
        }
    }
}