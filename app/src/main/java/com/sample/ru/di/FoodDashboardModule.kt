package com.sample.ru.di

import com.sample.ru.data.network.FoodDashboardRestService
import com.sample.ru.data.repository.FoodDashboardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FoodDashboardModule {

    @Provides
    @ViewModelScoped
    fun provideFoodDashboardRepository(
        @AppModule.FoodDashboardRemoteApi foodDashboardRestService: FoodDashboardRestService
    ) = FoodDashboardRepository(foodDashboardRestService)

}