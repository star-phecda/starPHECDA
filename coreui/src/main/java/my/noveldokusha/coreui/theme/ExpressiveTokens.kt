package my.noveldokusha.coreui.theme

import androidx.compose.ui.unit.dp

/**
 * Shared dimensions for the app's expressive component language.
 * Keep spacing and corner values centralized so screens do not invent local scales.
 */
object ExpressiveTokens {
    val cornerExtraSmall = 4.dp
    val cornerSmall = 8.dp
    val cornerMedium = 12.dp
    val cornerLarge = 20.dp
    val cornerExtraLarge = 28.dp
    val cornerExtraLargeIncreased = 36.dp

    // Kept as a compatibility alias. Capsule components use CircleShape instead.
    val pillCorner = cornerExtraLargeIncreased

    // Four-dp spacing rhythm.
    val space1 = 4.dp
    val space2 = 8.dp
    val space3 = 12.dp
    val space4 = 16.dp
    val space5 = 20.dp
    val space6 = 24.dp
    val space7 = 32.dp
    val space8 = 40.dp
    val space9 = 48.dp
    val space10 = 64.dp

    // Responsive page gutters and readable content widths.
    val compactPageGutter = 16.dp
    val mediumPageGutter = 24.dp
    val expandedPageGutter = 32.dp

    val compactContentMaxWidth = 600.dp
    val mediumContentMaxWidth = 840.dp
    val expandedContentMaxWidth = 1200.dp

    val minimumBookGridCellWidth = 128.dp
    const val maximumBookGridColumns = 6

    val navigationHeight = 68.dp
    val compactNavigationHeight = 56.dp
    val navigationItemHeight = 48.dp

    const val navigationSelectedItemWeight = 1.85f
    const val bookCoverPressedScale = 0.985f

    const val motionQuickMillis = 150
    const val motionStandardMillis = 250
    const val motionEmphasizedMillis = 300
}
