package my.noveldokusha.coreui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.material.color.utilities.Hct
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ArtworkColorSchemeTest {

    @Test
    fun expressiveArtworkPaletteUsesTheCoverAsItsAccentSeed() {
        val base = lightColorScheme()
        val result = createArtworkColorScheme(
            seedColor = Color(0xFFDE573A),
            isDark = false,
            baseScheme = base
        )

        assertNotEquals(base.primary, result.primary)
        assertEquals(base.error, result.error)
        assertEquals(base.onError, result.onError)
    }

    @Test
    fun neutralArtworkProducesALessSaturatedPaletteThanExpressiveArtwork() {
        val baseScheme = androidx.compose.material3.darkColorScheme()
        val neutralResult = createArtworkColorScheme(
            seedColor = Color(0xFF888888),
            isDark = true,
            baseScheme = baseScheme
        )
        val expressiveResult = createArtworkColorScheme(
            seedColor = Color(0xFFDE573A),
            isDark = true,
            baseScheme = baseScheme
        )

        val neutralPrimaryChroma = Hct.fromInt(neutralResult.primary.toArgb()).chroma
        val expressivePrimaryChroma = Hct.fromInt(expressiveResult.primary.toArgb()).chroma

        assertTrue(
            "Neutral artwork should produce a less saturated primary than expressive artwork " +
                "(neutral=$neutralPrimaryChroma, expressive=$expressivePrimaryChroma)",
            neutralPrimaryChroma < expressivePrimaryChroma
        )
    }
}
