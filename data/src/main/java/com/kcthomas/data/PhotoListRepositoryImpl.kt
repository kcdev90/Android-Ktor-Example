package com.kcthomas.data

import com.kcthomas.domain.PhotoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PhotoListRepositoryImpl(
    private val remoteSource: RemotePhotoListSource
    // TODO - Add a Cached source
) : PhotoListRepository {

    override suspend fun getPhotoList() = withContext(Dispatchers.IO) {
        try {
            remoteSource.getPhotoList()
        } catch (e: Exception) {
            null
        }
    }

}
