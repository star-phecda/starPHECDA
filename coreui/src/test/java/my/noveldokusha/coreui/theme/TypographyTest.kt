package my.noveldokusha.coreui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class TypographyTest {
    @Test
    fun interfaceAndDisplayRolesUseDistinctCustomFamilies() {
        val interfaceFamily = typography.bodyLarge.fontFamily

        assertNotEquals(FontFamily.SansSerif, interfaceFamily)
        assertEquals(interfaceFamily, typography.headlineLarge.fontFamily)
        assertEquals(interfaceFamily, typography.titleLarge.fontFamily)
        assertEquals(interfaceFamily, typography.labelLarge.fontFamily)

        assertNotEquals(FontFamily.SansSerif, typography.displayLarge.fontFamily)
        assertNotEquals(interfaceFamily, typography.displayLarge.fontFamily)
        assertNotEquals(FontFamily.SansSerif, typography.displayMedium.fontFamily)
        assertNotEquals(FontFamily.SansSerif, typography.displaySmall.fontFamily)
    }

    @Test
    fun materialRolesFollowTheExpressiveSizeAndLineHeightScale() {
        assertRole(typography.displayLarge, 40, 48)
        assertRole(typography.displayMedium, 32, 40)
        assertRole(typography.displaySmall, 28, 36)
        assertRole(typography.headlineLarge, 28, 36)
        assertRole(typography.headlineMedium, 24, 32)
        assertRole(typography.headlineSmall, 20, 28)
        assertRole(typography.titleLarge, 20, 26)
        assertRole(typography.titleMedium, 16, 22)
        assertRole(typography.titleSmall, 14, 20)
        assertRole(typography.bodyLarge, 16, 24)
        assertRole(typography.bodyMedium, 14, 20)
        assertRole(typography.bodySmall, 12, 16)
        assertRole(typography.labelLarge, 14, 20)
        assertRole(typography.labelMedium, 12, 16)
        assertRole(typography.labelSmall, 11, 16)
    }

    @Test
    fun interfaceWeightsRemainDistinctFromBodyCopy() {
        assertEquals(FontWeight.Bold, typography.headlineLarge.fontWeight)
        assertEquals(FontWeight.SemiBold, typography.titleLarge.fontWeight)
        assertEquals(FontWeight.Normal, typography.bodyLarge.fontWeight)
        assertEquals(FontWeight.SemiBold, typography.labelLarge.fontWeight)
    }

    private fun assertRole(
        style: androidx.compose.ui.text.TextStyle,
        size: Int,
        lineHeight: Int,
    ) {
        assertEquals(size.sp, style.fontSize)
        assertEquals(lineHeight.sp, style.lineHeight)
    }
}