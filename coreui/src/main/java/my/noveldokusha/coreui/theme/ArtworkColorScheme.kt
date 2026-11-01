@file:Suppress("RestrictedApi")

package my.noveldokusha.coreui.theme

import android.graphics.Bitmap
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.color.utilities.DynamicScheme
import com.google.android.material.color.utilities.Hct
import com.google.android.material.color.utilities.QuantizerCelebi
import com.google.android.material.color.utilities.SchemeExpressive
import com.google.android.material.color.utilities.SchemeMonochrome

/**
 * Extracts a representative seed from a cover image.
 *
 * Call from a background dispatcher for large images. The bitmap is never recycled or mutated.
 * Sampling is capped at 128px to keep palette extraction inexpensive.
 */
fun extractArtworkSeedColor(
    bitmap: Bitmap,
    maxDimension: Int = 128
): Color {
    if (bitmap.isRecycled || bitmap.width <= 0 || bitmap.height <= 0) {
        return Color(0xFF6750A4)
    }

    val sample = if (
        maxDimension > 0 &&
        (bitmap.width > maxDimension || bitmap.height > maxDimension)
    ) {
        val scale = maxDimension.toFloat() / maxOf(bitmap.width, bitmap.height).toFloat()
        Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * scale).toInt().coerceAtLeast(1),
            (bitmap.height * scale).toInt().coerceAtLeast(1),
            true
        )
    } else {
        bitmap
    }

    return try {
        val pixels = IntArray(sample.width * sample.height)
        sample.getPixels(pixels, 0, sample.width, 0, 0, sample.width, sample.height)
        val visiblePixels = pixels.filter { ((it ushr 24) and 0xFF) >= 32 }.toIntArray()
        if (visiblePixels.isEmpty()) return Color(0xFF6750A4)

        val quantized = QuantizerCelebi.quantize(visiblePixels, 64)
        val seedArgb = quantized.entries.maxByOrNull { entry ->
            val chroma = Hct.fromInt(entry.key).chroma
            val populationScore = entry.value.toDouble() / visiblePixels.size.toDouble()
            val chromaScore = (chroma / 80.0).coerceIn(0.0, 1.0)
            populationScore * 0.65 + chromaScore * 0.35
        }?.key ?: visiblePixels[visiblePixels.size / 2]

        Color(seedArgb)
    } catch (_: Exception) {
        Color(0xFF6750A4)
    } finally {
        if (sample !== bitmap && !sample.isRecycled) sample.recycle()
    }
}

/**
 * Builds an artwork-derived Material 3 palette. Neutral covers stay neutral instead of
 * introducing an unrelated saturated accent.
 */
internal fun createArtworkColorScheme(
    seedColor: Color,
    isDark: Boolean,
    baseScheme: ColorScheme
): ColorScheme {
    val sourceHct = Hct.fromInt(seedColor.toArgb())
    val dynamicScheme: DynamicScheme = if (sourceHct.chroma <= 8.0) {
        SchemeMonochrome(sourceHct, isDark, 0.0)
    } else {
        SchemeExpressive(sourceHct, isDark, 0.0)
    }
    return dynamicScheme.toComposeColorScheme(baseScheme)
}

private fun DynamicScheme.toComposeColorScheme(base: ColorScheme): ColorScheme = base.copy(
    primary = Color(getPrimary()),
    onPrimary = Color(getOnPrimary()),
    primaryContainer = Color(getPrimaryContainer()),
    onPrimaryContainer = Color(getOnPrimaryContainer()),
    inversePrimary = Color(getInversePrimary()),
    secondary = Color(getSecondary()),
    onSecondary = Color(getOnSecondary()),
    secondaryContainer = Color(getSecondaryContainer()),
    onSecondaryContainer = Color(getOnSecondaryContainer()),
    tertiary = Color(getTertiary()),
    onTertiary = Color(getOnTertiary()),
    tertiaryContainer = Color(getTertiaryContainer()),
    onTertiaryContainer = Color(getOnTertiaryContainer()),
    background = Color(getBackground()),
    onBackground = Color(getOnBackground()),
    surface = Color(getSurface()),
    onSurface = Color(getOnSurface()),
    surfaceVariant = Color(getSurfaceVariant()),
    onSurfaceVariant = Color(getOnSurfaceVariant()),
    surfaceTint = Color(getSurfaceTint()),
    inverseSurface = Color(getInverseSurface()),
    inverseOnSurface = Color(getInverseOnSurface()),
    outline = Color(getOutline()),
    outlineVariant = Color(getOutlineVariant()),
    surfaceBright = Color(getSurfaceBright()),
    surfaceDim = Color(getSurfaceDim()),
    surfaceContainer = Color(getSurfaceContainer()),
    surfaceContainerHigh = Color(getSurfaceContainerHigh()),
    surfaceContainerHighest = Color(getSurfaceContainerHighest()),
    surfaceContainerLow = Color(getSurfaceContainerLow()),
    surfaceContainerLowest = Color(getSurfaceContainerLowest()),
    primaryFixed = Color(getPrimaryFixed()),
    primaryFixedDim = Color(getPrimaryFixedDim()),
    onPrimaryFixed = Color(getOnPrimaryFixed()),
    onPrimaryFixedVariant = Color(getOnPrimaryFixedVariant()),
    secondaryFixed = Color(getSecondaryFixed()),
    secondaryFixedDim = Color(getSecondaryFixedDim()),
    onSecondaryFixed = Color(getOnSecondaryFixed()),
    onSecondaryFixedVariant = Color(getOnSecondaryFixedVariant()),
    tertiaryFixed = Color(getTertiaryFixed()),
    tertiaryFixedDim = Color(getTertiaryFixedDim()),
    onTertiaryFixed = Color(getOnTertiaryFixed()),
    onTertiaryFixedVariant = Color(getOnTertiaryFixedVariant())
)

/**
 * Opt-in theme wrapper for a screen or surface with a known cover bitmap seed.
 * It intentionally preserves the parent typography and shapes.
 */
@Composable
fun ArtworkTheme(
    seedColor: Color?,
    content: @Composable () -> Unit
) {
    if (seedColor == null) {
        content()
        return
    }

    val baseScheme = MaterialTheme.colorScheme
    val isDark = LocalIsDark.current
    val artworkScheme = remember(seedColor, isDark, baseScheme) {
        createArtworkColorScheme(seedColor, isDark, baseScheme)
    }

    MaterialTheme(
        colorScheme = artworkScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
