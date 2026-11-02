package my.noveldokusha.coreui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import my.noveldokusha.coreui.R
import my.noveldokusha.coreui.composableActions.ListGridLoadWatcher
import my.noveldokusha.coreui.states.IteratorState
import my.noveldokusha.core.Response
import my.noveldokusha.core.appPreferences.ListLayoutMode
import my.noveldokusha.core.appPreferences.SourceStripPosition
import my.noveldokusha.core.domain.CloudfareVerificationBypassFailedException
import my.noveldokusha.core.domain.WebViewCookieManagerInitializationFailedException
import my.noveldokusha.core.rememberResolvedBookImagePath
import my.noveldokusha.coreui.theme.ExpressiveContentFrame
import my.noveldokusha.coreui.theme.ExpressiveTokens
import my.noveldokusha.coreui.theme.adaptiveGridColumnCount
import my.noveldokusha.feature.local_database.BookMetadata

@Composable
fun BooksVerticalView(
    list: List<BookMetadata>,
    state: LazyGridState,
    error: Response.Error?,
    loadState: IteratorState,
    layoutMode: ListLayoutMode,
    onLoadNext: () -> Unit,
    onBookClicked: (book: BookMetadata) -> Unit,
    onBookLongClicked: (bookItem: BookMetadata) -> Unit,
    onReload: () -> Unit = {},
    onCopyError: (String) -> Unit = {},
    onWebViewOpen: () -> Unit = {},
    // Количество колонок из общего preference BOOKS_GRID_COLUMNS (2..6, дефолт 3).
    // Оно остаётся базовой плотностью на компактных окнах.
    gridColumns: Int = 3,
    sourceStripPosition: SourceStripPosition = SourceStripPosition.BelowCover,
    innerPadding: PaddingValues = PaddingValues(),
    referer: String? = null,
    translatedTitles: Map<String, String> = emptyMap(),
    coverOverrides: Map<String, String> = emptyMap(),
    topLeftBadge: (@Composable (BookMetadata) -> Unit)? = null,
) {
    ListGridLoadWatcher(
        listState = state,
        loadState = loadState,
        onLoadNext = onLoadNext
    )

    ExpressiveContentFrame(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val adaptiveColumns = adaptiveGridColumnCount(
                availableWidth = maxWidth,
                preferredColumns = gridColumns,
            )
            val columns by remember(layoutMode, adaptiveColumns) {
                derivedStateOf {
                    when (layoutMode) {
                        ListLayoutMode.VerticalList -> GridCells.Fixed(1)
                        ListLayoutMode.VerticalGrid -> GridCells.Fixed(adaptiveColumns)
                    }
                }
            }

            LazyVerticalGrid(
                columns = columns,
                state = state,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(ExpressiveTokens.space3),
                verticalArrangement = Arrangement.spacedBy(ExpressiveTokens.space3),
                contentPadding = PaddingValues(
                    top = ExpressiveTokens.space2,
                    bottom = 160.dp,
                )
            ) {
                items(
                    items = list,
                    key = { it.url },
                    contentType = { if (layoutMode == ListLayoutMode.VerticalList) 1 else 2 }
                ) {
                    val title = translatedTitles[it.url] ?: it.title
                    when (layoutMode) {
                        ListLayoutMode.VerticalList -> MyButton(
                            text = title,
                            onClick = { onBookClicked(it) },
                            onLongClick = { onBookLongClicked(it) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ListLayoutMode.VerticalGrid -> BookImageButtonView(
                            title = title,
                            coverImageModel = rememberResolvedBookImagePath(
                                bookUrl = it.url,
                                imagePath = coverOverrides[it.url] ?: it.coverImageUrl
                            ),
                            onClick = { onBookClicked(it) },
                            onLongClick = { onBookLongClicked(it) },
                            topRightBadge = { BookRatingBadge(rating = it.rating) },
                            topLeftBadge = topLeftBadge?.let { badge -> { badge(it) } },
                            sourceStripPosition = sourceStripPosition,
                            referer = referer,
                        )
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                    ) {
                        when (loadState) {
                            IteratorState.LOADING -> Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(ExpressiveTokens.space4)
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = ExpressiveTokens.space2)
                                )
                                Text(
                                    text = stringResource(R.string.loading),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            IteratorState.CONSUMED -> Text(
                                text = when {
                                    list.isEmpty() -> stringResource(R.string.no_results_found)
                                    else -> stringResource(R.string.no_more_results)
                                },
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            else -> Unit
                        }
                    }
                }

                when (error?.exception) {
                    is WebViewCookieManagerInitializationFailedException -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            ClickableOption(
                                title = stringResource(R.string.cloudfare_firewall_detected),
                                subtitle = stringResource(R.string.please_open_web_view_and_try_verify_then_reload),
                                onClick = onWebViewOpen,
                                modifier = Modifier.padding(ExpressiveTokens.space3)
                            )
                        }
                    }
                    is CloudfareVerificationBypassFailedException -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            ClickableOption(
                                title = stringResource(R.string.cloudfare_firewall_failed_bypass),
                                subtitle = stringResource(R.string.tried_but_failed_to_bypass_cloudfare_firewall),
                                onClick = onWebViewOpen,
                                modifier = Modifier.padding(ExpressiveTokens.space3),
                                isButton = true
                            )
                        }
                    }
                    null -> Unit
                }

                if (error != null) item(span = { GridItemSpan(maxLineSpan) }) {
                    ErrorView(error = error.message, onReload = onReload, onCopyError = onCopyError)
                }
            }
        }
    }
}
