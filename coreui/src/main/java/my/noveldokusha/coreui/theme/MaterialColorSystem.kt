package my.noveldokusha.coreui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance

/**
 * Builds a consistent Material 3 surface ladder from the selected palette's own surface.
 * Blending toward black/white preserves each preset's neutral tint while making surface
 * containers read as one ordered tonal system.
 */
internal fun ColorScheme.withExpressiveSurfaceHierarchy(isDark: Boolean): ColorScheme {
    val baseSurface = surface
    return if (isDark) {
        copy(
            surfaceDim = lerp(baseSurface, Color.Black, 0.26f),
            surfaceBright = lerp(baseSurface, Color.White, 0.20f),
            surfaceContainerLowest = lerp(baseSurface, Color.Black, 0.22f),
            surfaceContainerLow = lerp(baseSurface, Color.Black, 0.12f),
            surfaceContainer = baseSurface,
            surfaceContainerHigh = lerp(baseSurface, Color.White, 0.06f),
            surfaceContainerHighest = lerp(baseSurface, Color.White, 0.13f),
        )
    } else {
        copy(
            surfaceDim = lerp(baseSurface, Color.Black, 0.16f),
            surfaceBright = lerp(baseSurface, Color.White, 0.20f),
            surfaceContainerLowest = lerp(baseSurface, Color.White, 0.16f),
            surfaceContainerLow = lerp(baseSurface, Color.White, 0.08f),
            surfaceContainer = baseSurface,
            surfaceContainerHigh = lerp(baseSurface, Color.Black, 0.04f),
            surfaceContainerHighest = lerp(baseSurface, Color.Black, 0.08f),
        )
    }
}

/**
 * Keeps foreground roles that already meet WCAG AA and minimally adjusts the ones that do not.
 * Text-oriented Material 3 foreground/background pairs target a 4.5:1 contrast ratio.
 */
internal fun ColorScheme.withAccessibleContentRoles(): ColorScheme {
    val accessiblePrimary = primary.withMinimumContrast(surface)
    val accessibleSecondary = secondary.withMinimumContrast(surface)
    val accessibleTertiary = tertiary.withMinimumContrast(surface)
    val accessibleError = error.withMinimumContrast(surface)

    return copy(
        primary = accessiblePrimary,
        onPrimary = onPrimary.withMinimumContrast(accessiblePrimary),
        onPrimaryContainer = onPrimaryContainer.withMinimumContrast(primaryContainer, surface),
        secondary = accessibleSecondary,
        onSecondary = onSecondary.withMinimumContrast(accessibleSecondary),
        onSecondaryContainer = onSecondaryContainer.withMinimumContrast(secondaryContainer, surface),
        tertiary = accessibleTertiary,
        onTertiary = onTertiary.withMinimumContrast(accessibleTertiary),
        onTertiaryContainer = onTertiaryContainer.withMinimumContrast(tertiaryContainer, surface),
        error = accessibleError,
        onError = onError.withMinimumContrast(accessibleError),
        onErrorContainer = onErrorContainer.withMinimumContrast(errorContainer, surface),
        onBackground = onBackground.withMinimumContrast(background),
        onSurface = onSurface.withMinimumContrast(surface),
        onSurfaceVariant = onSurfaceVariant.withMinimumContrast(surfaceVariant, surface),
        inversePrimary = inversePrimary.withMinimumContrast(inverseSurface),
        inverseOnSurface = inverseOnSurface.withMinimumContrast(inverseSurface),
        onPrimaryFixed = onPrimaryFixed.withMinimumContrast(primaryFixed),
        onPrimaryFixedVariant = onPrimaryFixedVariant.withMinimumContrast(primaryFixed),
        onSecondaryFixed = onSecondaryFixed.withMinimumContrast(secondaryFixed),
        onSecondaryFixedVariant = onSecondaryFixedVariant.withMinimumContrast(secondaryFixed),
        onTertiaryFixed = onTertiaryFixed.withMinimumContrast(tertiaryFixed),
        onTertiaryFixedVariant = onTertiaryFixedVariant.withMinimumContrast(tertiaryFixed),
    )
}

internal fun Color.contrastRatioAgainst(background: Color): Float {
    val displayedForeground = compositeOver(background)
    val foregroundLuminance = displayedForeground.luminance().coerceIn(0f, 1f)
    val backgroundLuminance = background.luminance().coerceIn(0f, 1f)
    val lighter = maxOf(foregroundLuminance, backgroundLuminance)
    val darker = minOf(foregroundLuminance, backgroundLuminance)
    return (lighter + 0.05f) / (darker + 0.05f)
}

private fun Color.withMinimumContrast(
    background: Color,
    backgroundBehind: Color? = null,
    minimumRatio: Float = 4.5f,
): Color {
    val displayedBackground = if (background.alpha < 1f && backgroundBehind != null) {
        background.compositeOver(backgroundBehind)
    } else {
        background
    }
    if (contrastRatioAgainst(displayedBackground) >= minimumRatio) return this

    val blackContrast = Color.Black.contrastRatioAgainst(displayedBackground)
    val whiteContrast = Color.White.contrastRatioAgainst(displayedBackground)
    val target = if (blackContrast >= whiteContrast) Color.Black else Color.White

    var lower = 0f
    var upper = 1f
    repeat(28) {
        val middle = (lower + upper) / 2f
        if (lerp(this, target, middle).contrastRatioAgainst(displayedBackground) >= minimumRatio) {
            upper = middle
        } else {
            lower = middle
        }
    }
    return lerp(this, target, upper)
}
