package my.noveldokusha.coreui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateFloatAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.noveldokusha.coreui.theme.ExpressiveTokens

data class ExpressiveNavigationItem(
    val label: String,
    val icon: Painter,
    val selectedIcon: Painter? = null
)

/**
 * A floating, labelled navigation pill with a tonal selection indicator and spring motion.
 *
 * Keep this component in coreui so the app shell and future feature shells share the same
 * selection geometry, colour roles and interaction behaviour.
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
        shape = RoundedCornerShape(ExpressiveTokens.pillCorner),
        color = colors.surfaceContainerHigh.copy(alpha = 0.96f),
        shadowElevation = 6.dp,
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    if (compactMode) {
                        ExpressiveTokens.compactNavigationHeight
                    } else {
                        ExpressiveTokens.navigationHeight
                    }
                )
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val selected = selectedIndex == index
                val iconColor by animateColorAsState(
                    targetValue = if (selected) colors.primary else colors.onSurfaceVariant,
                    animationSpec = tween(ExpressiveTokens.motionQuickMillis),
                    label = "navigation-icon-color"
                )
                val labelColor by animateColorAsState(
                    targetValue = if (selected) colors.primary else colors.onSurfaceVariant,
                    animationSpec = tween(ExpressiveTokens.motionQuickMillis),
                    label = "navigation-label-color"
                )
                val iconScale by animateFloatAsState(
                    targetValue = if (selected) 1.08f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "navigation-icon-scale"
                )
                val interactionSource = remember { MutableInteractionSource() }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedVisibility(
                            visible = selected,
                            enter = fadeIn(tween(100)) + scaleIn(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            ),
                            exit = fadeOut(tween(100)) + scaleOut(
                                animationSpec = tween(100)
                            )
                        ) {
                            Surface(
                                modifier = Modifier.size(
                                    ExpressiveTokens.navigationIndicatorWidth,
                                    ExpressiveTokens.navigationIndicatorHeight
                                ),
                                shape = RoundedCornerShape(16.dp),
                                color = colors.secondaryContainer
                            ) {}
                        }

                        Box(
                            modifier = Modifier
                                .size(
                                    ExpressiveTokens.navigationIconContainerWidth,
                                    ExpressiveTokens.navigationIconContainerHeight
                                )
                                .graphicsLayer {
                                    scaleX = iconScale
                                    scaleY = iconScale
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = item.selectedIcon ?: item.icon,
                                contentDescription = item.label,
                                tint = iconColor,
                                modifier = Modifier.size(ExpressiveTokens.navigationIconSize)
                            )
                        }
                    }

                    if (!compactMode) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.label,
                            maxLines = 1,
                            color = labelColor,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 12.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }
    }
}
