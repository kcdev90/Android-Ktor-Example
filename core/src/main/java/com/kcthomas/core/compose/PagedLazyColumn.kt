package com.kcthomas.core.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kcthomas.core.R
import com.kcthomas.domain.Photo
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun PagedLazyColumn(
    pageState: PageState<Photo>,
    refresh: (() -> Unit)? = null,
    loadNextPage: () -> Unit,
    content: LazyListScope.() -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(pageState.isInflight),
        onRefresh = { refresh?.invoke() },
        swipeEnabled = refresh != null
    ) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                pageState.isError && pageState.page == 1 -> {
                    item("errorFirstPage") {
                        // TODO - Show Snackbar
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.size(150.dp),
                                painter = painterResource(R.drawable.ic_warning),
                                contentDescription = stringResource(R.string.photo_list_error_load)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.photo_list_error_load),
                                fontStyle = FontStyle.Italic,
                                fontSize = 24.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                pageState.data.isEmpty() && !pageState.isInflight -> {
                    item("noData") {
                        Text(
                            text = stringResource(R.string.photo_list_no_data),
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    }
                }

                else -> {
                    content()

                    when {
                        !pageState.isInflight && pageState.page > 1 -> {
                            item("progressIndicator") {
                                Box(Modifier.fillMaxWidth(), Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        pageState.isError && pageState.page == 1 -> {
                            item("errorNextPage") {
                                Box(Modifier.fillMaxWidth(), Alignment.Center) {
                                    Text(
                                        text = stringResource(R.string.photo_list_error_load_next_page),
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Blue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!pageState.isInflight) {
            LaunchedEffect(lazyListState) {
                snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
                    .map { items -> items.map { info -> info.key } }
                    .distinctUntilChanged()
                    .filter { keys -> keys.firstOrNull { key -> key == "progressIndicator" } != null }
                    .collect {
                        loadNextPage()
                    }
            }
        }
    }
}

data class PageState <out T>(
    val data: List<T> = emptyList(),
    val isInflight: Boolean = true,
    val isError: Boolean = false,
    val page: Int = 1,
)
