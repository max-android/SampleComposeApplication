package com.sample.ru.data.repository

import com.sample.ru.data.model.FoodDashboardModel
import com.sample.ru.data.network.FoodDashboardRestService
import com.sample.ru.util.getBodyDto

class FoodDashboardRepository(
    private val fdRestService: FoodDashboardRestService
) {

    suspend fun loadFoodDashboard() {
        try {
            val data: FoodDashboardModel = fdRestService.foodDashboard().getBodyDto()
        } catch (throwable: Throwable) {

        }
    }

}