package com.kcthomas.data

import com.kcthomas.domain.Photo

interface PhotoListService {

    suspend fun getPhotoList(): List<Photo>?

}
