package dev.tomaswild.portfolio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import dev.tomaswild.portfolio.resources.Inter_VariableFont_wght
import dev.tomaswild.portfolio.resources.PlayfairDisplay_VariableFont_wght
import dev.tomaswild.portfolio.resources.Res
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

val baseline = Typography()

@Composable
fun AppTypography(): Typography {
    val bodyFont = variableFamily(resource = Res.font.Inter_VariableFont_wght)
    val displayFont = variableFamily(resource = Res.font.PlayfairDisplay_VariableFont_wght)

    return remember(key1 = bodyFont, key2 = displayFont) {
        with(receiver = baseline) {
            copy(
                displayLarge = displayLarge.copy(fontFamily = displayFont),
                displayMedium = displayMedium.copy(fontFamily = displayFont),
                displaySmall = displaySmall.copy(fontFamily = displayFont),
                headlineLarge = headlineLarge.copy(fontFamily = displayFont),
                headlineMedium = headlineMedium.copy(fontFamily = displayFont),
                headlineSmall = headlineSmall.copy(fontFamily = displayFont),
                titleLarge = titleLarge.copy(fontFamily = bodyFont),
                titleMedium = titleMedium.copy(fontFamily = bodyFont),
                titleSmall = titleSmall.copy(fontFamily = bodyFont),
                bodyLarge = bodyLarge.copy(fontFamily = bodyFont),
                bodyMedium = bodyMedium.copy(fontFamily = bodyFont),
                bodySmall = bodySmall.copy(fontFamily = bodyFont),
                labelLarge = labelLarge.copy(fontFamily = bodyFont),
                labelMedium = labelMedium.copy(fontFamily = bodyFont),
                labelSmall = labelSmall.copy(fontFamily = bodyFont),
            )
        }
    }
}

@Composable
private fun variableFamily(
    resource: FontResource,
    weights: List<FontWeight> = listOf(FontWeight.Normal, FontWeight.Medium, FontWeight.SemiBold, FontWeight.Bold),
): FontFamily = FontFamily(
    fonts = weights.map { weight ->
        Font(
            resource = resource,
            weight = weight,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(value = weight.weight),
            ),
        )
    }
)