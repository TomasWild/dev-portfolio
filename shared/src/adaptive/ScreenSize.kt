package dev.tomaswild.portfolio.adaptive

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

enum class ScreenSize {
    COMPACT,
    MEDIUM,
    EXPANDED,
}

@Composable
fun currentScreenSize(): ScreenSize {
    val sizeClass = currentWindowAdaptiveInfoV2().windowSizeClass

    return when {
        sizeClass.isWidthAtLeastBreakpoint(
            widthDpBreakpoint = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND,
        ) -> ScreenSize.EXPANDED

        sizeClass.isWidthAtLeastBreakpoint(
            widthDpBreakpoint = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND,
        ) -> ScreenSize.MEDIUM

        else -> ScreenSize.COMPACT
    }
}