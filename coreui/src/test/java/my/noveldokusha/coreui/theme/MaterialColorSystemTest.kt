package my.noveldokusha.coreui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.compositeOver
import my.noveldokusha.coreui.theme.colorscheme.BaseColorScheme
import my.noveldokusha.coreui.theme.colorscheme.*
import org.junit.Assert.assertTrue
import org.junit.Test

class MaterialColorSystemTest {

    private val staticPalettes: List<Pair<String, BaseColorScheme>> = listOf(
        "Tachiyomi" to TachiyomiColorScheme,
        "Green Apple" to GreenAppleColorScheme,
        "Lavender" to LavenderColorScheme,
        "Midnight Dusk" to MidnightDuskColorScheme,
        "Strawberry Daiquiri" to StrawberryDaiquiriColorScheme,
        "Tako" to TakoColorScheme,
        "Teal Turquoise" to TealTurquoiseColorScheme,
        "Tidal Wave" to TidalWaveColorScheme,
        "Yotsuba" to YotsubaColorScheme,
        "Monochrome" to MonochromeColorScheme,
        "Catppuccin" to CatppuccinColorScheme,
        "Nord" to NordColorScheme,
        "Yin Yang" to YinYangColorScheme,
        "Cloudflare" to CloudflareColorScheme,
        "Cotton Candy" to CottoncandyColorScheme,
        "Doom" to DoomColorScheme,
        "Matrix" to MatrixColorScheme,
        "Mocha" to MochaColorScheme,
        "Sapphire" to SapphireColorScheme,
    )

    @Test
    fun everyBuiltInPaletteHasAccessibleTextRolePairsInBothModes() {
        staticPalettes.forEach { (name, palette) ->
            listOf(false, true).forEach { isDark ->
                val source = if (isDark) palette.darkScheme else palette.lightScheme
                val scheme = source
                    .withExpressiveSurfaceHierarchy(isDark)
                    .withAccessibleContentRoles()
                val mode = if (isDark) "dark" else "light"

                assertContrast("$name $mode primary", scheme.primary, scheme.surface)
                assertContrast("$name $mode secondary", scheme.secondary, scheme.surface)
                assertContrast("$name $mode tertiary", scheme.tertiary, scheme.surface)
                assertContrast("$name $mode error", scheme.error, scheme.surface)
                assertContrast("$name $mode onPrimary", scheme.onPrimary, scheme.primary)
                assertContrast("$name $mode onPrimaryContainer", scheme.onPrimaryContainer, scheme.primaryContainer.compositeOver(scheme.surface))
                assertContrast("$name $mode onSecondary", scheme.onSecondary, scheme.secondary)
                assertContrast("$name $mode onSecondaryContainer", scheme.onSecondaryContainer, scheme.secondaryContainer.compositeOver(scheme.surface))
                assertContrast("$name $mode onTertiary", scheme.onTertiary, scheme.tertiary)
                assertContrast("$name $mode onTertiaryContainer", scheme.onTertiaryContainer, scheme.tertiaryContainer.compositeOver(scheme.surface))
                assertContrast("$name $mode onError", scheme.onError, scheme.error)
                assertContrast("$name $mode onErrorContainer", scheme.onErrorContainer, scheme.errorContainer.compositeOver(scheme.surface))
                assertContrast("$name $mode onBackground", scheme.onBackground, scheme.background)
                assertContrast("$name $mode onSurface", scheme.onSurface, scheme.surface)
                assertContrast("$name $mode onSurfaceVariant", scheme.onSurfaceVariant, scheme.surfaceVariant.compositeOver(scheme.surface))
                assertContrast("$name $mode inverseOnSurface", scheme.inverseOnSurface, scheme.inverseSurface)
                assertContrast("$name $mode onPrimaryFixed", scheme.onPrimaryFixed, scheme.primaryFixed)
                assertContrast("$name $mode onPrimaryFixedVariant", scheme.onPrimaryFixedVariant, scheme.primaryFixed)
                assertContrast("$name $mode onSecondaryFixed", scheme.onSecondaryFixed, scheme.secondaryFixed)
                assertContrast("$name $mode onSecondaryFixedVariant", scheme.onSecondaryFixedVariant, scheme.secondaryFixed)
                assertContrast("$name $mode onTertiaryFixed", scheme.onTertiaryFixed, scheme.tertiaryFixed)
                assertContrast("$name $mode onTertiaryFixedVariant", scheme.onTertiaryFixedVariant, scheme.tertiaryFixed)
            }
        }
    }

    @Test
    fun lightSurfaceContainersFollowTheMaterialTonalHierarchy() {
        val scheme = lightColorScheme(surface = Color(0xFFECE7F0))
            .withExpressiveSurfaceHierarchy(isDark = false)
        val tones = listOf(
            scheme.surfaceContainerHighest,
            scheme.surfaceContainerHigh,
            scheme.surfaceContainer,
            scheme.surfaceContainerLow,
            scheme.surfaceContainerLowest,
        ).map { it.luminance() }

        assertStrictlyAscending("light surface hierarchy", tones)
        assertTrue(scheme.surfaceDim.luminance() < scheme.surface.luminance())
        assertTrue(scheme.surfaceBright.luminance() > scheme.surfaceContainerLowest.luminance())
    }

    @Test
    fun darkSurfaceContainersFollowTheMaterialTonalHierarchy() {
        val scheme = darkColorScheme(surface = Color(0xFF252333))
            .withExpressiveSurfaceHierarchy(isDark = true)
        val tones = listOf(
            scheme.surfaceContainerLowest,
            scheme.surfaceContainerLow,
            scheme.surfaceContainer,
            scheme.surfaceContainerHigh,
            scheme.surfaceContainerHighest,
        ).map { it.luminance() }

        assertStrictlyAscending("dark surface hierarchy", tones)
        assertTrue(scheme.surfaceDim.luminance() < scheme.surfaceContainerLowest.luminance())
        assertTrue(scheme.surfaceBright.luminance() > scheme.surfaceContainerHighest.luminance())
    }

    @Test
    fun artworkDerivedPalettesKeepTextReadableInLightAndDarkModes() {
        listOf(false, true).forEach { isDark ->
            val base = if (isDark) darkColorScheme() else lightColorScheme()
            val scheme = createArtworkColorScheme(
                seedColor = Color(0xFFDE573A),
                isDark = isDark,
                baseScheme = base,
            )
            val mode = if (isDark) "dark artwork" else "light artwork"

            assertContrast("$mode onPrimary", scheme.onPrimary, scheme.primary)
            assertContrast("$mode onPrimaryContainer", scheme.onPrimaryContainer, scheme.primaryContainer.compositeOver(scheme.surface))
            assertContrast("$mode onSurface", scheme.onSurface, scheme.surface)
            assertContrast("$mode onSurfaceVariant", scheme.onSurfaceVariant, scheme.surfaceVariant.compositeOver(scheme.surface))
            assertContrast("$mode inverseOnSurface", scheme.inverseOnSurface, scheme.inverseSurface)
        }
    }

    private fun assertContrast(label: String, foreground: Color, background: Color) {
        val ratio = foreground.contrastRatioAgainst(background)
        assertTrue("$label contrast should be at least 4.5:1, was $ratio", ratio >= 4.49f)
    }

    private fun assertStrictlyAscending(label: String, values: List<Float>) {
        values.zipWithNext().forEachIndexed { index, (lower, upper) ->
            assertTrue("$label did not increase at step $index: $lower -> $upper", lower < upper)
        }
    }
}
