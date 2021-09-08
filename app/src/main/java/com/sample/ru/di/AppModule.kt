package com.sample.ru.di

import com.sample.ru.BuildConfig
import com.sample.ru.data.network.MemesRestService
import com.sample.ru.data.network.NewsRestService
import com.sample.ru.data.network.PhotoRestService
import com.sample.ru.util.createRestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class NewsRemoteApi

    @NewsRemoteApi
    @Provides
    @Singleton
    fun provideNewsRestService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NewsRestService = createRestService(
        gsonConverterFactory,
        okHttpClient,
        BuildConfig.NEWS_BASE_URL,
        NewsRestService::class.java
    )

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class MemesRemoteApi

    @MemesRemoteApi
    @Provides
    @Singleton
    fun provideMemesRestService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): MemesRestService = createRestService(
        gsonConverterFactory,
        okHttpClient,
        BuildConfig.MEMES_BASE_URL,
        MemesRestService::class.java
    )

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PhotoRemoteApi

    @PhotoRemoteApi
    @Provides
    @Singleton
    fun providePhotoRestService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): PhotoRestService = createRestService(
        gsonConverterFactory,
        okHttpClient,
        BuildConfig.PHOTO_BASE_URL,
        PhotoRestService::class.java
    )

}