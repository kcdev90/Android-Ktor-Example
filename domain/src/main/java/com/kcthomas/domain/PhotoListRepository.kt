package com.kcthomas.domain

interface PhotoListRepository {

    // return null if network/server error
    suspend fun getPhotoList(): List<Photo>?

}
