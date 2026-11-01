package my.noveldokusha.settings.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrightnessMedium
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import my.noveldokusha.coreui.theme.AppTheme
import my.noveldokusha.coreui.theme.DarkMode
import my.noveldokusha.coreui.theme.ExpressiveShapes
import my.noveldokusha.coreui.theme.colorAccent
import my.noveldokusha.coreui.theme.rememberAppThemePalettePreview
import my.noveldokusha.coreui.theme.textPadding
import my.noveldokusha.settings.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SettingsTheme(
    currentAppTheme: AppTheme,
    currentDarkMode: DarkMode,
    onAppThemeChange: (AppTheme) -> Unit,
    onDarkModeChange: (DarkMode) -> Unit,
) {
    Column {
        Text(
            text = stringResource(id = R.string.theme),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.textPadding(),
            color = colorAccent()
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DarkMode.entries.forEach { mode ->
                FilterChip(
                    shape = ExpressiveShapes.pill,
                    selected = mode == currentDarkMode,
                    onClick = { onDarkModeChange(mode) },
                    label = { Text(text = stringResource(id = mode.titleRes)) },
                    leadingIcon = {
                        Icon(
                            imageVector = when (mode) {
                                DarkMode.SYSTEM -> Icons.Outlined.BrightnessMedium
                                DarkMode.LIGHT -> Icons.Outlined.LightMode
                                DarkMode.DARK -> Icons.Outlined.DarkMode
                                DarkMode.BLACK -> Icons.Outlined.Nightlight
                            },
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.color_scheme),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(AppTheme.entries) { theme ->
                ThemePreviewChip(
                    theme = theme,
                    isSelected = theme == currentAppTheme,
                    onClick = { onAppThemeChange(theme) },
                )
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun ThemePreviewChip(
    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val palette = rememberAppThemePalettePreview(theme)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(ExpressiveShapes.pill)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = onClick
            )
            .width(72.dp)
            .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .width(66.dp)
                .height(34.dp)
                .clip(ExpressiveShapes.pill)
                .background(palette.surfaceContainer)
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outlineVariant,
                    shape = ExpressiveShapes.pill,
                )
                .padding(horizontal = 7.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            listOf(palette.primary, palette.secondary, palette.tertiary).forEach { swatch ->
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(swatch)
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = stringResource(id = theme.titleRes),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
