package my.noveldokusha.coreui.theme

import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

class ExpressiveLayoutTokensTest {
    @Test
    fun spacingScaleUsesTheApprovedFourDpRhythm() {
        assertEquals(4.dp, ExpressiveTokens.space1)
        assertEquals(8.dp, ExpressiveTokens.space2)
        assertEquals(12.dp, ExpressiveTokens.space3)
        assertEquals(16.dp, ExpressiveTokens.space4)
        assertEquals(20.dp, ExpressiveTokens.space5)
        assertEquals(24.dp, ExpressiveTokens.space6)
        assertEquals(32.dp, ExpressiveTokens.space7)
        assertEquals(40.dp, ExpressiveTokens.space8)
        assertEquals(48.dp, ExpressiveTokens.space9)
        assertEquals(64.dp, ExpressiveTokens.space10)
    }

    @Test
    fun windowSizeClassUsesTheApprovedBreakpointBoundaries() {
        assertEquals(ExpressiveWindowSizeClass.COMPACT, expressiveWindowSizeClass(599.dp))
        assertEquals(ExpressiveWindowSizeClass.MEDIUM, expressiveWindowSizeClass(600.dp))
        assertEquals(ExpressiveWindowSizeClass.MEDIUM, expressiveWindowSizeClass(839.dp))
        assertEquals(ExpressiveWindowSizeClass.EXPANDED, expressiveWindowSizeClass(840.dp))
    }

    @Test
    fun contentGuttersFollowTheWindowSizeClass() {
        assertEquals(16.dp, expressiveContentGutter(ExpressiveWindowSizeClass.COMPACT))
        assertEquals(24.dp, expressiveContentGutter(ExpressiveWindowSizeClass.MEDIUM))
        assertEquals(32.dp, expressiveContentGutter(ExpressiveWindowSizeClass.EXPANDED))
    }

    @Test
    fun contentMaximumWidthFollowsTheWindowSizeClass() {
        assertEquals(600.dp, expressiveContentMaxWidth(ExpressiveWindowSizeClass.COMPACT))
        assertEquals(840.dp, expressiveContentMaxWidth(ExpressiveWindowSizeClass.MEDIUM))
        assertEquals(1200.dp, expressiveContentMaxWidth(ExpressiveWindowSizeClass.EXPANDED))
    }

    @Test
    fun gridAddsColumnsWhenMoreWidthIsAvailable() {
        assertEquals(
            3,
            adaptiveGridColumnCount(
                availableWidth = 320.dp,
                preferredColumns = 3,
                minCellWidth = 128.dp,
            ),
        )
        assertEquals(
            5,
            adaptiveGridColumnCount(
                availableWidth = 640.dp,
                preferredColumns = 3,
                minCellWidth = 128.dp,
            ),
        )
        assertEquals(
            6,
            adaptiveGridColumnCount(
                availableWidth = 1280.dp,
                preferredColumns = 3,
                minCellWidth = 128.dp,
            ),
        )
    }

    @Test
    fun gridPreservesAndBoundsThePreferredColumnCount() {
        assertEquals(
            2,
            adaptiveGridColumnCount(
                availableWidth = 320.dp,
                preferredColumns = 1,
                minCellWidth = 128.dp,
            ),
        )
        assertEquals(
            6,
            adaptiveGridColumnCount(
                availableWidth = 320.dp,
                preferredColumns = 9,
                minCellWidth = 128.dp,
            ),
        )
        assertEquals(
            4,
            adaptiveGridColumnCount(
                availableWidth = 1280.dp,
                preferredColumns = 2,
                minCellWidth = 128.dp,
                maxColumns = 4,
            ),
        )
    }

    @Test
    fun invalidMinimumCellWidthFallsBackToTheSmallestSafeValue() {
        assertEquals(
            6,
            adaptiveGridColumnCount(
                availableWidth = 1280.dp,
                preferredColumns = 3,
                minCellWidth = 0.dp,
            ),
        )
    }
}
