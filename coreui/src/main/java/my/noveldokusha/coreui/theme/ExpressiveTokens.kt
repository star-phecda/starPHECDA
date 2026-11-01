package my.noveldokusha.coreui.theme

import androidx.compose.ui.unit.dp

/**
 * Shared dimensions for the app's expressive component language.
 * Keep these values centralized so screens do not invent their own corner scale.
 */
object ExpressiveTokens {
    val cornerSmall = 8.dp
    val cornerMedium = 16.dp
    val cornerLarge = 24.dp
    val cornerExtraLarge = 32.dp
    val pillCorner = 36.dp

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
