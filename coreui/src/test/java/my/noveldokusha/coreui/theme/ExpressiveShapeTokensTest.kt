package my.noveldokusha.coreui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
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
        assertRoundedCorner(ExpressiveShapes.dropdown, 12.dp)
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
    }

    @Test
    fun imageBorderUsesTheSharedBookCoverShape() {
        assertEquals(ExpressiveShapes.bookCover, ImageBorderShape)
    }

    private fun assertRoundedCorner(shape: Shape, radius: androidx.compose.ui.unit.Dp) {
        assertEquals(RoundedCornerShape(radius), shape)
    }
}
