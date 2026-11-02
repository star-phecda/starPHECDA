package my.noveldokusha.coreui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import my.noveldokusha.coreui.components.expressiveNavigationItemWeight
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class ExpressiveShapeTokensTest {
    @Test
    fun canonicalCornerScaleUsesExpressiveDimensions() {
        assertEquals(4.dp, ExpressiveTokens.cornerExtraSmall)
        assertEquals(8.dp, ExpressiveTokens.cornerSmall)
        assertEquals(12.dp, ExpressiveTokens.cornerMedium)
        assertEquals(20.dp, ExpressiveTokens.cornerLarge)
        assertEquals(28.dp, ExpressiveTokens.cornerExtraLarge)
        assertEquals(36.dp, ExpressiveTokens.cornerExtraLargeIncreased)
        assertEquals(36.dp, ExpressiveTokens.pillCorner)
        assertEquals(68.dp, ExpressiveTokens.navigationHeight)
        assertEquals(56.dp, ExpressiveTokens.compactNavigationHeight)
        assertEquals(48.dp, ExpressiveTokens.navigationItemHeight)
        assertEquals(0.985f, ExpressiveTokens.bookCoverPressedScale, 0f)
    }

    @Test
    fun materialShapeRolesMapToTheCanonicalScale() {
        assertRoundedCorner(shapes.extraSmall, 4.dp)
        assertRoundedCorner(shapes.small, 8.dp)
        assertRoundedCorner(shapes.medium, 12.dp)
        assertRoundedCorner(shapes.large, 20.dp)
        assertRoundedCorner(shapes.extraLarge, 28.dp)
    }

    @Test
    fun componentShapesExpressTheirIntendedRoles() {
        assertRoundedCorner(ExpressiveShapes.bookCover, 20.dp)
        assertRoundedCorner(ExpressiveShapes.card, 20.dp)
        assertRoundedCorner(ExpressiveShapes.container, 20.dp)
        assertRoundedCorner(ExpressiveShapes.compactContainer, 8.dp)
        assertRoundedCorner(ExpressiveShapes.option, 12.dp)
        assertEquals(
            RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
            ExpressiveShapes.dropdown,
        )
        assertRoundedCorner(ExpressiveShapes.button, 20.dp)
        assertRoundedCorner(ExpressiveShapes.featuredCard, 28.dp)
        assertRoundedCorner(ExpressiveShapes.dialog, 28.dp)
        assertRoundedCorner(ExpressiveShapes.selectedButton, 36.dp)
        assertSame(CircleShape, ExpressiveShapes.pill)
        assertEquals(
            RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
            ExpressiveShapes.sheet,
        )
        assertRoundedCorner(ExpressiveShapes.input, 12.dp)
        assertEquals(
            RoundedCornerShape(topStart = 36.dp, bottomStart = 36.dp),
            ExpressiveShapes.sideSheet,
        )
    }

    @Test
    fun imageBorderUsesTheSharedBookCoverShape() {
        assertEquals(ExpressiveShapes.bookCover, ImageBorderShape)
    }

    @Test
    fun selectedNavigationItemReceivesAnExpandedShare() {
        assertEquals(1.85f, expressiveNavigationItemWeight(selected = true, compactMode = false), 0f)
    }

    @Test
    fun unselectedNavigationItemKeepsItsCompactShare() {
        assertEquals(1f, expressiveNavigationItemWeight(selected = false, compactMode = false), 0f)
    }

    @Test
    fun compactNavigationKeepsEqualIconTargets() {
        assertEquals(1f, expressiveNavigationItemWeight(selected = true, compactMode = true), 0f)
    }

    private fun assertRoundedCorner(shape: Shape, radius: androidx.compose.ui.unit.Dp) {
        assertEquals(RoundedCornerShape(radius), shape)
    }
}
