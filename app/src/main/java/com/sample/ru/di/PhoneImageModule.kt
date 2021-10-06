package com.sample.ru.di

import com.sample.ru.data.repository.PhoneImageRepository
import com.sample.ru.data.service.FileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object PhoneImageModule {

    @Provides
    @ViewModelScoped
    fun providePhoneImageRepository(
        fileService: FileService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = PhoneImageRepository(fileService, ioDispatcher)
}