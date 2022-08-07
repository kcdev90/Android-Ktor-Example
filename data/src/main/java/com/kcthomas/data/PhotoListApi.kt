package com.kcthomas.data

import com.kcthomas.domain.Photo
import retrofit2.http.GET

interface PhotoListApi {

    @GET("v2/list")
    suspend fun getPhotoList(): List<Photo>?

}
