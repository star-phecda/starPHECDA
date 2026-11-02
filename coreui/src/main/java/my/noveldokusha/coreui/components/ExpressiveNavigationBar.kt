package my.noveldokusha.coreui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import my.noveldokusha.coreui.theme.ExpressiveShapes
import my.noveldokusha.coreui.theme.ExpressiveTokens

data class ExpressiveNavigationItem(
    val label: String,
    val icon: Painter,
    val selectedIcon: Painter? = null
)

/** Width share for one item in the floating navigation row. */
internal fun expressiveNavigationItemWeight(selected: Boolean, compactMode: Boolean): Float =
    if (selected && !compactMode) ExpressiveTokens.navigationSelectedItemWeight else 1f

/**
 * Floating navigation with a spring-expanded active destination.
 *
 * Inactive destinations stay icon-first; the selected destination grows to make room
 * for its label. Compact mode keeps every destination equally sized and icon-only.
 */
@Composable
fun ExpressiveNavigationBar(
    items: List<ExpressiveNavigationItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    compactMode: Boolean = false
) {
    if (items.isEmpty()) return

    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = modifier,
        shape = ExpressiveShapes.pill,
        color = colors.surfaceContainerLow.copy(alpha = 0.98f),
        contentColor = colors.onSurface,
        border = BorderStroke(1.dp, colors.outlineVariant.copy(alpha = 0.55f)),
        shadowElevation = 8.dp,
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    if (compactMode) ExpressiveTokens.compactNavigationHeight
                    else ExpressiveTokens.navigationHeight
                )
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val selected = selectedIndex == index
                val itemWeight by animateFloatAsState(
                    targetValue = expressiveNavigationItemWeight(selected, compactMode),
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    ),
                    label = "navigation-item-weight"
                )
                val iconScale by animateFloatAsState(
                    targetValue = if (selected) 1.08f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "navigation-icon-scale"
                )
                val itemColor by animateColorAsState(
                    targetValue = if (selected) colors.secondaryContainer else Color.Transparent,
                    animationSpec = tween(ExpressiveTokens.motionStandardMillis),
                    label = "navigation-item-container"
                )
                val contentColor by animateColorAsState(
                    targetValue = if (selected) colors.onSecondaryContainer else colors.onSurfaceVariant,
                    animationSpec = tween(ExpressiveTokens.motionQuickMillis),
                    label = "navigation-content-color"
                )
                val interactionSource = remember(item.label) { MutableInteractionSource() }

                Column(
                    modifier = Modifier
                        .weight(itemWeight)
                        .fillMaxHeight()
                        .clip(ExpressiveShapes.pill)
                        .selectable(
                            selected = selected,
                            onClick = { onItemSelected(index) },
                            role = Role.Tab,
                            interactionSource = interactionSource,
                            indication = null
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ExpressiveTokens.navigationItemHeight),
                        shape = ExpressiveShapes.pill,
                        color = itemColor
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = if (selected && !compactMode) 10.dp else 0.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(28.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = if (selected) item.selectedIcon ?: item.icon else item.icon,
                                    contentDescription = item.label,
                                    tint = contentColor,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .graphicsLayer {
                                            scaleX = iconScale
                                            scaleY = iconScale
                                        }
                                )
                            }

                            AnimatedVisibility(
                                visible = selected && !compactMode,
                                enter = fadeIn(tween(120)) + expandHorizontally(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMediumLow
                                    ),
                                    expandFrom = Alignment.Start
                                ),
                                exit = fadeOut(tween(90)) + shrinkHorizontally(
                                    animationSpec = tween(120),
                                    shrinkTowards = Alignment.Start
                                )
                            ) {
                                Text(
                                    text = item.label,
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis,
                                    color = contentColor,
                                    modifier = Modifier
                                        .padding(start = 7.dp)
                                        .clearAndSetSemantics { },
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
