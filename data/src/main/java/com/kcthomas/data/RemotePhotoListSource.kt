package com.kcthomas.data

class RemotePhotoListSource (private val api: PhotoListApi) {

    suspend fun getPhotoList() = api.getPhotoList()

}
