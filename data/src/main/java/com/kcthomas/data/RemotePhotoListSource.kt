package com.kcthomas.data

import com.kcthomas.domain.Photo
import io.ktor.client.request.get
import io.ktor.client.request.url

class RemotePhotoListSource : PhotoListService {

    companion object {
        const val BASE_URL = "https://picsum.photos"
    }

    override suspend fun getPhotoList(): List<Photo>? =
        KtorClient.client.get {
            url("$BASE_URL/v2/list")
        }

}
