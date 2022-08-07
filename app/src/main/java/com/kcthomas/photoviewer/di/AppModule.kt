package com.kcthomas.photoviewer.di

import com.kcthomas.data.PhotoListApi
import com.kcthomas.data.PhotoListRepositoryImpl
import com.kcthomas.data.RemotePhotoListSource
import com.kcthomas.domain.PhotoListRepository
import com.kcthomas.domain.usecases.GetPhotoListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
            )
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(PhotoListApi::class.java)

    @Provides
    @Singleton
    fun provideRemotePhotoListSource(api: PhotoListApi) = RemotePhotoListSource(api)

    @Provides
    @Singleton
    fun providePhotoListRepository(remoteSource: RemotePhotoListSource) =
        PhotoListRepositoryImpl(remoteSource)

    @Provides
    @Singleton
    fun provideGetPhotoListUseCase(repository: PhotoListRepositoryImpl) =
        GetPhotoListUseCase(repository)

}
