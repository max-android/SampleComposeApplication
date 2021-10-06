package com.sample.ru.di

import android.content.Context
import com.sample.ru.BuildConfig
import com.sample.ru.data.local.ProfileSettings
import com.sample.ru.data.network.*
import com.sample.ru.data.service.CacheService
import com.sample.ru.data.service.FileService
import com.sample.ru.util.createRestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class FoodDashboardRemoteApi

    @FoodDashboardRemoteApi
    @Provides
    @Singleton
    fun provideFoodDashboardRestService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): FoodDashboardRestService = createRestService(
        gsonConverterFactory,
        okHttpClient,
        BuildConfig.FOOD_DASHBOARD_BASE_URL,
        FoodDashboardRestService::class.java
    )

    @Provides
    @Singleton
    fun provideFoodService(): FoodService = FoodService()

    @Provides
    @Singleton
    fun provideThematicService(): ThematicService = ThematicService()

    @Provides
    @Singleton
    fun provideCacheService(): CacheService = CacheService()

    @Provides
    @Singleton
    fun provideProfileSettings(
        @ApplicationContext appContext: Context
    ): ProfileSettings = ProfileSettings(appContext)

    @Provides
    @Singleton
    fun provideFileService(
        @ApplicationContext appContext: Context
    ): FileService = FileService(appContext)

}