package com.sample.ru.di

import com.sample.ru.data.network.*
import com.sample.ru.data.repository.HomeRepository
import com.sample.ru.data.service.CacheService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        @AppModule.MemesRemoteApi memesRestService: MemesRestService,
        @AppModule.NewsRemoteApi newsRestService: NewsRestService,
        @AppModule.PhotoRemoteApi photoRestService: PhotoRestService,
        foodService: FoodService,
        thematicService: ThematicService,
        cacheService: CacheService
    ): HomeRepository = HomeRepository(
        memesRestService,
        newsRestService,
        photoRestService,
        foodService,
        thematicService,
        cacheService
    )

}