package dev.tomaswild.portfolio.section

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tomaswild.portfolio.adaptive.ScreenSize
import dev.tomaswild.portfolio.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private data class Project(
    val title: StringResource,
    val description: StringResource,
    val image: DrawableResource? = null,
    val tags: List<String>,
    val repositoryUrl: String? = null,
    val liveUrl: String? = null,
)

@Composable
fun Projects(
    screenSize: ScreenSize,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(space = 24.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            Text(
                text = stringResource(resource = Res.string.projects_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        val projects = listOf(
            Project(
                title = Res.string.project_developer_portfolio_title,
                description = Res.string.project_developer_portfolio_description,
                image = Res.drawable.project_dev_portfolio,
                tags = listOf("Kotlin", "Compose Multiplatform"),
                repositoryUrl = "https://github.com/TomasWild/dev-portfolio",
                liveUrl = "https://tomaswild.dev",
            ),
        )

        val columns = when (screenSize) {
            ScreenSize.COMPACT -> 1
            ScreenSize.MEDIUM -> 2
            ScreenSize.EXPANDED -> 3
        }

        projects.chunked(size = columns).forEach { rowProjects ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(space = 24.dp),
            ) {
                rowProjects.forEach { project ->
                    ProjectCard(
                        project = project,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(weight = 1F),
                    )
                }

                repeat(times = columns - rowProjects.size) {
                    Spacer(modifier = Modifier.weight(weight = 1F))
                }
            }
        }
    }
}

@Composable
private fun ProjectCard(
    project: Project,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(size = 24.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25F),
        ),
    ) {
        Column {
            ProjectImage(project = project)

            Column(
                modifier = Modifier
                    .weight(weight = 1F)
                    .padding(all = 24.dp),
                verticalArrangement = Arrangement.spacedBy(space = 12.dp),
            ) {
                Text(
                    text = stringResource(resource = project.title),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = stringResource(resource = project.description),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    lineHeight = 20.sp,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                ) {
                    project.tags.forEach { tag ->
                        TagItem(text = tag)
                    }
                }

                Spacer(modifier = Modifier.weight(weight = 1F))

                if (project.repositoryUrl != null || project.liveUrl != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                    ) {
                        val uriHandler = LocalUriHandler.current

                        project.repositoryUrl?.let { url ->
                            OutlinedButton(
                                onClick = { uriHandler.openUri(uri = url) },
                                shape = CircleShape,
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5F),
                                ),
                            ) {
                                Text(
                                    text = stringResource(resource = Res.string.project_code_button),
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }

                        project.liveUrl?.let { url ->
                            Button(
                                onClick = { uriHandler.openUri(uri = url) },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                ),
                            ) {
                                Text(
                                    text = stringResource(resource = Res.string.project_live_demo_button),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectImage(
    project: Project,
    modifier: Modifier = Modifier,
) {
    val imageModifier = modifier
        .fillMaxWidth()
        .height(height = 160.dp)

    if (project.image != null) {
        Image(
            painter = painterResource(resource = project.image),
            contentDescription = stringResource(resource = project.title),
            modifier = imageModifier,
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(
            modifier = imageModifier.background(
                Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.35F),
                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35F),
                    ),
                ),
            ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(resource = project.title).take(n = 1),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6F),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun TagItem(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12F),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}