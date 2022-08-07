package com.kcthomas.photoviewer.ui.photolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kcthomas.core.compose.PageState
import com.kcthomas.domain.Photo
import com.kcthomas.domain.usecases.GetPhotoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotoListUseCase: GetPhotoListUseCase
) : ViewModel() {

    private val _pageState = MutableStateFlow(PageState<Photo>())
    val pageState = _pageState.asStateFlow()

    init {
        loadPhotoList()
    }

    fun loadPhotoList(loadNextPage: Boolean = false) {
        Log.e(PhotoListViewModel::class.java.simpleName, "LoadPhotoList: $loadNextPage")
        viewModelScope.launch {
            _pageState.update {
                it.copy(
                    isError = false,
                    isInflight = true,
                    page = if (loadNextPage) it.page + 1 else 1
                )
            }
            getPhotoListUseCase.invoke().let { response ->
                if (response == null) {
                    Log.e(PhotoListViewModel::class.java.simpleName, "Failed to load Photos")
                    _pageState.update { it.copy(isError = true, isInflight = false) }
                } else {
                    _pageState.update {
                        it.copy(data = response, isInflight = false)
                    }
                }
            }
            Log.e(PhotoListViewModel::class.java.simpleName, "page: ${_pageState.value.page}")
        }
    }

}
