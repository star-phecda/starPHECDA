package my.noveldokusha.coreui.theme

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import my.noveldokusha.coreui.AppThemeProvider
import my.noveldokusha.coreui.theme.colorscheme.BaseColorScheme
import my.noveldokusha.coreui.theme.colorscheme.CatppuccinColorScheme
import my.noveldokusha.coreui.theme.colorscheme.CloudflareColorScheme
import my.noveldokusha.coreui.theme.colorscheme.CottoncandyColorScheme
import my.noveldokusha.coreui.theme.colorscheme.DoomColorScheme
import my.noveldokusha.coreui.theme.colorscheme.GreenAppleColorScheme
import my.noveldokusha.coreui.theme.colorscheme.LavenderColorScheme
import my.noveldokusha.coreui.theme.colorscheme.MatrixColorScheme
import my.noveldokusha.coreui.theme.colorscheme.MidnightDuskColorScheme
import my.noveldokusha.coreui.theme.colorscheme.MochaColorScheme
import my.noveldokusha.coreui.theme.colorscheme.MonetColorScheme
import my.noveldokusha.coreui.theme.colorscheme.MonochromeColorScheme
import my.noveldokusha.coreui.theme.colorscheme.NordColorScheme
import my.noveldokusha.coreui.theme.colorscheme.SapphireColorScheme
import my.noveldokusha.coreui.theme.colorscheme.StrawberryDaiquiriColorScheme
import my.noveldokusha.coreui.theme.colorscheme.TachiyomiColorScheme
import my.noveldokusha.coreui.theme.colorscheme.TakoColorScheme
import my.noveldokusha.coreui.theme.colorscheme.TealTurquoiseColorScheme
import my.noveldokusha.coreui.theme.colorscheme.TidalWaveColorScheme
import my.noveldokusha.coreui.theme.colorscheme.YinYangColorScheme
import my.noveldokusha.coreui.theme.colorscheme.YotsubaColorScheme

val LocalAppTheme = staticCompositionLocalOf<AppTheme> { AppTheme.DEFAULT }
val LocalIsDark = staticCompositionLocalOf<Boolean> { false }
val LocalIsAmoled = staticCompositionLocalOf<Boolean> { false }

@Immutable
data class AppThemePalettePreview(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val surfaceContainer: Color,
)

/** Returns swatches from the actual palette in the currently resolved light/dark mode. */
@Composable
fun rememberAppThemePalettePreview(theme: AppTheme): AppThemePalettePreview {
    val context = LocalContext.current
    val isDark = LocalIsDark.current
    val isAmoled = LocalIsAmoled.current
    val scheme = remember(context, theme, isDark, isAmoled) {
        createAppColorScheme(theme, context, isDark, isAmoled)
    }
    return remember(scheme) {
        AppThemePalettePreview(
            primary = scheme.primary,
            secondary = scheme.secondary,
            tertiary = scheme.tertiary,
            surfaceContainer = scheme.surfaceContainerLow,
        )
    }
}

@Composable
fun Theme(
    themeProvider: AppThemeProvider,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val darkMode = themeProvider.currentDarkMode(coroutineScope).value
    val resolvedAppTheme = themeProvider.currentAppTheme(coroutineScope).value

    val isDark = when (darkMode) {
        DarkMode.LIGHT -> false
        DarkMode.DARK -> true
        DarkMode.BLACK -> true
        DarkMode.SYSTEM -> isSystemInDarkTheme()
    }

    InternalTheme(
        appTheme = resolvedAppTheme,
        isDark = isDark,
        isAmoled = darkMode == DarkMode.BLACK,
        content = content,
    )
}

@Composable
fun Theme(
    appTheme: AppTheme = AppTheme.DEFAULT,
    darkMode: DarkMode = DarkMode.SYSTEM,
    content: @Composable () -> Unit,
) {
    val isDark = when (darkMode) {
        DarkMode.LIGHT -> false
        DarkMode.DARK -> true
        DarkMode.BLACK -> true
        DarkMode.SYSTEM -> isSystemInDarkTheme()
    }

    InternalTheme(
        appTheme = appTheme,
        isDark = isDark,
        isAmoled = darkMode == DarkMode.BLACK,
        content = content,
    )
}

@Composable
fun InternalTheme(
    appTheme: AppTheme = AppTheme.DEFAULT,
    isDark: Boolean = isSystemInDarkTheme(),
    isAmoled: Boolean = false,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val colorScheme = remember(context, appTheme, isDark, isAmoled) {
        createAppColorScheme(appTheme, context, isDark, isAmoled)
    }

    DisposableEffect(appTheme, isDark) {
        (context as? ComponentActivity)?.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ) { isDark },
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ) { isDark }
        )
        onDispose {}
    }

    val textSelectionColors = remember(colorScheme.primary) {
        TextSelectionColors(
            handleColor = colorScheme.primary,
            backgroundColor = colorScheme.primary.copy(alpha = 0.3f)
        )
    }

    Box(Modifier) {
        CompositionLocalProvider(
            LocalAppTheme provides appTheme,
            LocalIsDark provides isDark,
            LocalIsAmoled provides isAmoled,
        ) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = typography,
                shapes = shapes,
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onSurface,
                    LocalTextSelectionColors provides textSelectionColors,
                    content = content
                )
            }
        }
    }
}

@Composable
fun InternalTheme(
    darkMode: DarkMode = DarkMode.SYSTEM,
    appTheme: AppTheme = AppTheme.DEFAULT,
    content: @Composable () -> Unit,
) {
    InternalTheme(
        appTheme = appTheme,
        isDark = when (darkMode) {
            DarkMode.LIGHT -> false
            DarkMode.DARK, DarkMode.BLACK -> true
            DarkMode.SYSTEM -> isSystemInDarkTheme()
        },
        isAmoled = darkMode == DarkMode.BLACK,
        content = content,
    )
}

private fun createAppColorScheme(
    appTheme: AppTheme,
    context: Context,
    isDark: Boolean,
    isAmoled: Boolean,
): ColorScheme {
    return getBaseColorScheme(appTheme, context)
        .getColorScheme(isDark = isDark, isAmoled = isAmoled)
        .withExpressiveSurfaceHierarchy(isDark)
        .withAccessibleContentRoles()
}

private fun getBaseColorScheme(appTheme: AppTheme, context: Context): BaseColorScheme {
    return when (appTheme) {
        AppTheme.DEFAULT -> MonetColorScheme(context)
        AppTheme.TACHIYOMI -> TachiyomiColorScheme
        AppTheme.GREEN_APPLE -> GreenAppleColorScheme
        AppTheme.LAVENDER -> LavenderColorScheme
        AppTheme.MIDNIGHT_DUSK -> MidnightDuskColorScheme
        AppTheme.STRAWBERRY_DAIQUIRI -> StrawberryDaiquiriColorScheme
        AppTheme.TAKO -> TakoColorScheme
        AppTheme.TEALTURQUOISE -> TealTurquoiseColorScheme
        AppTheme.TIDAL_WAVE -> TidalWaveColorScheme
        AppTheme.YOTSUBA -> YotsubaColorScheme
        AppTheme.MONOCHROME -> MonochromeColorScheme
        AppTheme.CATPPUCCIN -> CatppuccinColorScheme
        AppTheme.NORD -> NordColorScheme
        AppTheme.YINYANG -> YinYangColorScheme
        AppTheme.CLOUDFLARE -> CloudflareColorScheme
        AppTheme.COTTONCANDY -> CottoncandyColorScheme
        AppTheme.DOOM -> DoomColorScheme
        AppTheme.MATRIX -> MatrixColorScheme
        AppTheme.MOCHA -> MochaColorScheme
        AppTheme.SAPPHIRE -> SapphireColorScheme
    }
}

