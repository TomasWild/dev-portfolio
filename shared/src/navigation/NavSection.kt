package dev.tomaswild.portfolio.navigation

import dev.tomaswild.portfolio.resources.*
import org.jetbrains.compose.resources.StringResource

enum class NavSection(
    val label: StringResource,
) {
    HOME(label = Res.string.navigation_home),
    ABOUT(label = Res.string.navigation_about),
    EXPERIENCE(label = Res.string.navigation_experience),
    PROJECTS(label = Res.string.navigation_projects),
    CONTACT(label = Res.string.navigation_contact),
}

fun NavSection.listIndex(): Int = when (this) {
    NavSection.HOME -> 0
    NavSection.ABOUT -> 1
    NavSection.EXPERIENCE -> 2
    NavSection.PROJECTS -> 3
    NavSection.CONTACT -> 4
}