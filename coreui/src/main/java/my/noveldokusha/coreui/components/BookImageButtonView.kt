package my.noveldokusha.coreui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.noveldokusha.core.appPreferences.SourceStripPosition
import my.noveldokusha.coreui.AppTestTags
import my.noveldokusha.coreui.R
import my.noveldokusha.coreui.theme.ExpressiveShapes
import my.noveldokusha.coreui.theme.ExpressiveTokens
import my.noveldokusha.coreui.theme.ImageBorderShape
import my.noveldokusha.coreui.theme.InternalTheme
import my.noveldokusha.coreui.theme.PreviewThemes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookImageButtonView(
    title: String,
    coverImageModel: Any,
    modifier: Modifier = Modifier,
    indication: Indication = LocalIndication.current,
    interactionSource: MutableInteractionSource? = null,
    topLeftBadge: (@Composable () -> Unit)? = null,
    topRightBadge: (@Composable () -> Unit)? = null,
    sourceStripUnreadCount: Int? = null,
    sourceStripSourceName: String? = null,
    sourceStripPosition: SourceStripPosition = SourceStripPosition.BelowCover,
    forceCache: Boolean = false,
    // Готовый заголовок Referer для загрузки обложки; null → из хоста картинки.
    referer: String? = null,
    fadeInDurationMillis: Int = 250,
    onClick: () -> Unit,
    onLongClick: () -> Unit = { },
) {
    val rememberedInteractionSource = remember { MutableInteractionSource() }
    val effectiveInteractionSource = interactionSource ?: rememberedInteractionSource
    val isPressed by effectiveInteractionSource.collectIsPressedAsState()
    val coverScale by animateFloatAsState(
        targetValue = if (isPressed) ExpressiveTokens.bookCoverPressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "book-cover-press-scale"
    )
    val stripUnreadCount = sourceStripUnreadCount
    val stripSourceName = sourceStripSourceName
    val showStrip = stripUnreadCount != null || stripSourceName != null
    val isOnCover = sourceStripPosition == SourceStripPosition.OnCover
    val isInfoPanel = sourceStripPosition == SourceStripPosition.InfoPanel
    val titleBottomPadding = if (showStrip && isOnCover) 32.dp else 8.dp

    Column(modifier = modifier.testTag(AppTestTags.BOOK_IMAGE_BUTTON_VIEW)) {
        Box(
            Modifier
                .padding(2.dp)
                .graphicsLayer {
                    scaleX = coverScale
                    scaleY = coverScale
                }
                .clip(ImageBorderShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.42f),
                    shape = ImageBorderShape
                )
                .fillMaxWidth()
                .aspectRatio(1 / 1.45f)
        ) {
            // Image
            Box(
                Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .combinedClickable(
                        indication = indication,
                        interactionSource = effectiveInteractionSource,
                        role = Role.Button,
                        onClick = onClick,
                        onLongClick = onLongClick
                    )
            ) {
                ImageView(
                    imageModel = coverImageModel,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    fadeInDurationMillis = fadeInDurationMillis,
                    error = R.drawable.default_book_cover,
                    forceCache = forceCache,
                    referer = referer,
                )
            }

            topLeftBadge?.let {
                Box(modifier = Modifier.align(Alignment.TopStart)) { it() }
            }
            topRightBadge?.let {
                Box(modifier = Modifier.align(Alignment.TopEnd)) { it() }
            }

            // One high-contrast title overlay avoids the heavy outlined double-text treatment.
            if (!isInfoPanel) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                0f to Color.Transparent,
                                0.42f to Color.Black.copy(alpha = 0.12f),
                                1f to Color.Black.copy(alpha = 0.72f),
                            )
                        )
                        .padding(top = 34.dp, bottom = titleBottomPadding)
                        .padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.55f),
                            offset = Offset(0f, 2f),
                            blurRadius = 4f
                        )
                    )
                )
            }

            // Source strip on cover
            if (isOnCover && showStrip) {
                SourceStrip(
                    unreadCount = stripUnreadCount,
                    sourceName = stripSourceName,
                    onCover = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .clip(ExpressiveShapes.pill)
                )
            }
        }

        // Below-cover: source strip or InfoPanel
        if (!isOnCover && showStrip && !isInfoPanel) {
            SourceStrip(
                unreadCount = stripUnreadCount,
                sourceName = stripSourceName,
                onCover = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
                    .clip(ExpressiveShapes.pill)
            )
        }

        // InfoPanel below cover — fixed 2-line height, centered
        if (isInfoPanel) {
            val twoLines = with(LocalDensity.current) { (16.sp * 2f).toDp() }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
                    .clip(ExpressiveShapes.compactContainer)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(twoLines),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 16.sp,
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.Both,
                            ),
                            lineBreak = LineBreak.Heading,
                            hyphens = Hyphens.Auto,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                if (showStrip) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (stripUnreadCount != null) {
                            Text(
                                text = stripUnreadCount.toString(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (stripSourceName != null) {
                                Text(
                                    text = " · ",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        if (stripSourceName != null) {
                            Text(
                                text = stripSourceName,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

/** Полоса «непрочитанные | источник»: фикс. окно 32.dp слева, делитель 1.dp, имя источника справа (weight). */
@Composable
private fun SourceStrip(
    unreadCount: Int?,
    sourceName: String?,
    onCover: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val stripBackground: Brush = if (onCover) {
        Brush.verticalGradient(
            0f to MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
            1f to MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.65f),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
            ),
            start = Offset.Zero,
            end = Offset.Infinite
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(22.dp)
            .background(stripBackground)
            .padding(horizontal = 6.dp)
    ) {
        if (unreadCount != null) {
            Box(
                modifier = Modifier.width(32.dp),
                contentAlignment = Alignment.Center
            ) {
                if (unreadCount == 0) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = unreadCount.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        )
                    )
                }
            }
            Box(
                Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f))
            )
        }
        if (sourceName != null) {
            Text(
                text = sourceName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp
                )
            )
        }
    }
}

/** Иконка типа контента */
fun String?.toContentTypeBadgeIcon(): Int = when (this) {
    "manga" -> R.drawable.ic_content_type_manga
    "video" -> R.drawable.ic_content_type_video
    else -> R.drawable.ic_content_type_novel
}

@PreviewThemes
@Composable
private fun PreviewView() {
    InternalTheme {
        Row {
            BookImageButtonView(
                title = "Hello there",
                coverImageModel = "",
                onClick = { },
                onLongClick = { },
                sourceStripUnreadCount = 0,
                sourceStripSourceName = "Local",
                sourceStripPosition = SourceStripPosition.BelowCover,
                modifier = Modifier.weight(1f)
            )
            BookImageButtonView(
                title = "Hello there text very long for a title, but many cases just like this",
                coverImageModel = "",
                onClick = { },
                onLongClick = { },
                sourceStripUnreadCount = 99999,
                sourceStripSourceName = "Very long source name that must be cut off with ellipsis",
                sourceStripPosition = SourceStripPosition.InfoPanel,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@PreviewThemes
@Composable
private fun PreviewInfoPanel() {
    InternalTheme {
        Row {
            BookImageButtonView(
                title = "Solo Leveling",
                coverImageModel = "",
                onClick = { },
                onLongClick = { },
                sourceStripUnreadCount = 12,
                sourceStripSourceName = "Source Name",
                sourceStripPosition = SourceStripPosition.InfoPanel,
                modifier = Modifier.weight(1f)
            )
            BookImageButtonView(
                title = "A very long novel title that should wrap to multiple lines",
                coverImageModel = "",
                onClick = { },
                onLongClick = { },
                sourceStripUnreadCount = 0,
                sourceStripSourceName = "Another Source",
                sourceStripPosition = SourceStripPosition.InfoPanel,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
