package my.noveldokusha.coreui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.floor

enum class ExpressiveWindowSizeClass {
    COMPACT,
    MEDIUM,
    EXPANDED,
}

val LocalExpressiveWindowSizeClass = staticCompositionLocalOf {
    ExpressiveWindowSizeClass.COMPACT
}

fun expressiveWindowSizeClass(width: Dp): ExpressiveWindowSizeClass = when {
    width < 600.dp -> ExpressiveWindowSizeClass.COMPACT
    width < 840.dp -> ExpressiveWindowSizeClass.MEDIUM
    else -> ExpressiveWindowSizeClass.EXPANDED
}

fun expressiveContentGutter(sizeClass: ExpressiveWindowSizeClass): Dp = when (sizeClass) {
    ExpressiveWindowSizeClass.COMPACT -> ExpressiveTokens.compactPageGutter
    ExpressiveWindowSizeClass.MEDIUM -> ExpressiveTokens.mediumPageGutter
    ExpressiveWindowSizeClass.EXPANDED -> ExpressiveTokens.expandedPageGutter
}

fun expressiveContentMaxWidth(sizeClass: ExpressiveWindowSizeClass): Dp = when (sizeClass) {
    ExpressiveWindowSizeClass.COMPACT -> ExpressiveTokens.compactContentMaxWidth
    ExpressiveWindowSizeClass.MEDIUM -> ExpressiveTokens.mediumContentMaxWidth
    ExpressiveWindowSizeClass.EXPANDED -> ExpressiveTokens.expandedContentMaxWidth
}

/**
 * Preserves a screen's preferred grid density while adding columns when a wider
 * window can accommodate more cards. The six-column ceiling matches the existing
 * library preference range.
 *
 * [availableWidth] is the width remaining after page gutters and scaffold insets.
 */
fun adaptiveGridColumnCount(
    availableWidth: Dp,
    preferredColumns: Int,
    minCellWidth: Dp = ExpressiveTokens.minimumBookGridCellWidth,
    maxColumns: Int = ExpressiveTokens.maximumBookGridColumns,
): Int {
    val columnLimit = maxColumns.coerceIn(2, ExpressiveTokens.maximumBookGridColumns)
    val preferred = preferredColumns.coerceIn(2, columnLimit)
    val safeMinimumCellWidth = minCellWidth.takeIf { it > 0.dp } ?: 1.dp
    val widthBasedColumns = floor(availableWidth.value / safeMinimumCellWidth.value)
        .toInt()
        .coerceIn(2, columnLimit)
    return maxOf(preferred, widthBasedColumns)
}

/**
 * Centers content on wide windows, limits its overall width, and applies the
 * approved page gutter. Screen-specific composables remain responsible for
 * their own vertical rhythm and internal composition.
 */
@Composable
fun ExpressiveContentFrame(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val sizeClass = LocalExpressiveWindowSizeClass.current
    val gutter = expressiveContentGutter(sizeClass)
    val maximumContentWidth = expressiveContentMaxWidth(sizeClass)

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = maximumContentWidth + gutter * 2f)
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = gutter),
                content = content,
            )
        }
    }
}
