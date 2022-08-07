package com.kcthomas.domain.usecases

import com.kcthomas.domain.PhotoListRepository

class GetPhotoListUseCase(private val repository: PhotoListRepository) {

    suspend fun invoke() = repository.getPhotoList()

}
