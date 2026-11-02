package my.noveldokusha.coreui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import my.noveldokusha.coreui.theme.ExpressiveShapes
import my.noveldokusha.coreui.theme.ExpressiveTokens

/**
 * Expressive navigation rail for medium and expanded windows.
 * Labels remain visible so wider layouts are navigable without relying on
 * icon recognition alone.
 */
@Composable
fun ExpressiveNavigationRail(
    items: List<ExpressiveNavigationItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (items.isEmpty()) return

    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = modifier.width(76.dp),
        shape = ExpressiveShapes.container,
        color = colors.surfaceContainerLow.copy(alpha = 0.98f),
        contentColor = colors.onSurface,
        border = BorderStroke(1.dp, colors.outlineVariant.copy(alpha = 0.55f)),
        shadowElevation = 6.dp,
        tonalElevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .padding(vertical = ExpressiveTokens.space3, horizontal = ExpressiveTokens.space2),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ExpressiveTokens.space3),
        ) {
            items.forEachIndexed { index, item ->
                val selected = selectedIndex == index
                val iconScale by animateFloatAsState(
                    targetValue = if (selected) 1.08f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium,
                    ),
                    label = "rail-icon-scale",
                )
                val containerColor by animateColorAsState(
                    targetValue = if (selected) colors.secondaryContainer else Color.Transparent,
                    animationSpec = tween(ExpressiveTokens.motionStandardMillis),
                    label = "rail-container-color",
                )
                val contentColor by animateColorAsState(
                    targetValue = if (selected) colors.onSecondaryContainer else colors.onSurfaceVariant,
                    animationSpec = tween(ExpressiveTokens.motionQuickMillis),
                    label = "rail-content-color",
                )
                val interactionSource = remember(item.label) { MutableInteractionSource() }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ExpressiveShapes.card)
                        .selectable(
                            selected = selected,
                            onClick = { onItemSelected(index) },
                            role = Role.Tab,
                            interactionSource = interactionSource,
                            indication = null,
                        )
                        .padding(vertical = ExpressiveTokens.space1),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(ExpressiveTokens.space1),
                ) {
                    Surface(
                        modifier = Modifier.size(width = 56.dp, height = 36.dp),
                        shape = CircleShape,
                        color = containerColor,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = if (selected) item.selectedIcon ?: item.icon else item.icon,
                                contentDescription = item.label,
                                tint = contentColor,
                                modifier = Modifier
                                    .size(22.dp)
                                    .graphicsLayer {
                                        scaleX = iconScale
                                        scaleY = iconScale
                                    },
                            )
                        }
                    }
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                        color = contentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
