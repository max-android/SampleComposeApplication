package com.sample.ru.di

import com.sample.ru.data.local.ProfileSettings
import com.sample.ru.data.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ProfileModule {

    @Provides
    @ViewModelScoped
    fun provideProfileRepository(
        profileSettings: ProfileSettings
    ) = ProfileRepository(profileSettings)

}