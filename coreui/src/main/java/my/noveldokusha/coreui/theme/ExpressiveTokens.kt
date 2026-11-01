package my.noveldokusha.coreui.theme

import androidx.compose.ui.unit.dp

/**
 * Shared dimensions for the app's expressive component language.
 * Keep corner values centralized so screens do not invent local shape scales.
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

    val navigationHeight = 76.dp
    val compactNavigationHeight = 64.dp
    val navigationIndicatorWidth = 64.dp
    val navigationIndicatorHeight = 32.dp
    val navigationIconContainerWidth = 48.dp
    val navigationIconContainerHeight = 28.dp
    val navigationIconSize = 24.dp

    const val motionQuickMillis = 150
    const val motionStandardMillis = 250
    const val motionEmphasizedMillis = 300
}
