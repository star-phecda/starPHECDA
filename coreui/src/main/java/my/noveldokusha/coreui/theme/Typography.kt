package my.noveldokusha.coreui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import my.noveldokusha.coreui.R

/**
 * Manrope is the interface voice: clear, contemporary, and expressive across its variable
 * weight axis. Every app-level Material role uses this family except the display roles.
 */
@OptIn(ExperimentalTextApi::class)
private val interfaceFontFamily = FontFamily(
    Font(
        R.font.manrope_variable,
        FontWeight.Normal,
        variationSettings = FontVariation.Settings(FontWeight.Normal, FontStyle.Normal),
    ),
    Font(
        R.font.manrope_variable,
        FontWeight.Medium,
        variationSettings = FontVariation.Settings(FontWeight.Medium, FontStyle.Normal),
    ),
    Font(
        R.font.manrope_variable,
        FontWeight.SemiBold,
        variationSettings = FontVariation.Settings(FontWeight.SemiBold, FontStyle.Normal),
    ),
    Font(
        R.font.manrope_variable,
        FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontWeight.Bold, FontStyle.Normal),
    ),
)

/**
 * Literata adds a literary display voice without making utility text or reading controls serif.
 * The optical-size axis is tuned to the nominal size of each Material display role.
 */
@OptIn(ExperimentalTextApi::class)
private fun literataDisplayFamily(size: TextUnit, weight: FontWeight): FontFamily = FontFamily(
    Font(
        resId = R.font.literata_variable,
        weight = weight,
        style = FontStyle.Normal,
        variationSettings = FontVariation.Settings(
            weight,
            FontStyle.Normal,
            FontVariation.opticalSizing(size),
        ),
    ),
)

private val displayLargeFontFamily = literataDisplayFamily(40.sp, FontWeight.SemiBold)
private val displayMediumFontFamily = literataDisplayFamily(32.sp, FontWeight.SemiBold)
private val displaySmallFontFamily = literataDisplayFamily(28.sp, FontWeight.Medium)

/**
 * Application typography for navigation, library, discovery, history and settings.
 *
 * The reader keeps its own reading typography and font preferences. This scale is intentionally
 * tuned for interface hierarchy rather than long-form chapter text.
 */
val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = displayLargeFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = (-0.7).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = displayMediumFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.45).sp,
    ),
    displaySmall = TextStyle(
        fontFamily = displaySmallFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.2).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.35).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.2).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = (-0.1).sp,
    ),
    titleMedium = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.25.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = interfaceFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.3.sp,
    ),
)