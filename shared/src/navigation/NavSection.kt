package dev.tomaswild.portfolio.navigation

import dev.tomaswild.portfolio.resources.*
import org.jetbrains.compose.resources.StringResource

enum class NavSection(
    val label: StringResource,
) {
    ABOUT(label = Res.string.navigation_about),
    EXPERIENCE(label = Res.string.navigation_experience),
    PROJECTS(label = Res.string.navigation_projects),
    CONTACT(label = Res.string.navigation_contact),
}