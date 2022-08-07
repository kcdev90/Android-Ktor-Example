package com.kcthomas.photoviewer.ui.photolist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kcthomas.core.compose.PagedLazyColumn
import com.kcthomas.photoviewer.ui.photolist.views.PhotoCard

@Composable
fun PhotoListScreen(
    viewModel: PhotoListViewModel = hiltViewModel()
) {
    val pageState by viewModel.pageState.collectAsState()

    PagedLazyColumn(
        pageState = pageState,
        refresh = { viewModel.loadPhotoList(loadNextPage = false) },
        loadNextPage = { viewModel.loadPhotoList(loadNextPage = true) },
    ) {
        pageState.data.forEach { photo ->
            item {
                PhotoCard(photo) {
                    Log.e("t","${photo.author} clicked")
                }
            }
        }
    }
}
