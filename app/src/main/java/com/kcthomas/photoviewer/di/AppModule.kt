package com.kcthomas.photoviewer.di

import com.kcthomas.data.PhotoListRepositoryImpl
import com.kcthomas.data.RemotePhotoListSource
import com.kcthomas.domain.usecases.GetPhotoListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRemotePhotoListSource() = RemotePhotoListSource()

    @Provides
    @Singleton
    fun providePhotoListRepository(remoteSource: RemotePhotoListSource) =
        PhotoListRepositoryImpl(remoteSource)

    @Provides
    @Singleton
    fun provideGetPhotoListUseCase(repository: PhotoListRepositoryImpl) =
        GetPhotoListUseCase(repository)

}
