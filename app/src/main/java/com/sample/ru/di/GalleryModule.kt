package com.sample.ru.di

import com.sample.ru.data.repository.GalleryRepository
import com.sample.ru.data.service.CacheService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GalleryModule {

    @Provides
    @ViewModelScoped
    fun provideGalleryRepository(cacheService: CacheService) = GalleryRepository(cacheService)

}