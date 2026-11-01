package my.noveldokusha.coreui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Named geometry for the app's component roles.
 *
 * Pills use CircleShape so their ends stay fully rounded regardless of component height.
 */
object ExpressiveShapes {
    val bookCover: Shape = RoundedCornerShape(ExpressiveTokens.cornerLarge)
    val card: Shape = RoundedCornerShape(ExpressiveTokens.cornerLarge)
    val featuredCard: Shape = RoundedCornerShape(ExpressiveTokens.cornerExtraLarge)
    val container: Shape = RoundedCornerShape(ExpressiveTokens.cornerLarge)
    val compactContainer: Shape = RoundedCornerShape(ExpressiveTokens.cornerSmall)
    val option: Shape = RoundedCornerShape(ExpressiveTokens.cornerMedium)
    val button: Shape = RoundedCornerShape(ExpressiveTokens.cornerLarge)
    val selectedButton: Shape = RoundedCornerShape(ExpressiveTokens.cornerExtraLargeIncreased)
    val pill: Shape = CircleShape
    val input: Shape = RoundedCornerShape(ExpressiveTokens.cornerMedium)
    val dropdown: Shape = RoundedCornerShape(
        bottomStart = ExpressiveTokens.cornerMedium,
        bottomEnd = ExpressiveTokens.cornerMedium,
    )
    val sheet: Shape = RoundedCornerShape(
        topStart = ExpressiveTokens.cornerExtraLargeIncreased,
        topEnd = ExpressiveTokens.cornerExtraLargeIncreased,
    )
    val dialog: Shape = RoundedCornerShape(ExpressiveTokens.cornerExtraLarge)
}

val shapes = Shapes(
    extraSmall = RoundedCornerShape(ExpressiveTokens.cornerExtraSmall),
    small = RoundedCornerShape(ExpressiveTokens.cornerSmall),
    medium = RoundedCornerShape(ExpressiveTokens.cornerMedium),
    large = RoundedCornerShape(ExpressiveTokens.cornerLarge),
    extraLarge = RoundedCornerShape(ExpressiveTokens.cornerExtraLarge),
)

val ImageBorderShape = ExpressiveShapes.bookCover
val selectableMinHeight = 48.dp
