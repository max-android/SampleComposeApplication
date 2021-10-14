package com.sample.ru.data.repository

import com.sample.ru.data.mapper.toListFoodModel
import com.sample.ru.data.network.FoodDashboardRestService
import com.sample.ru.features.foodDashboard.*
import com.sample.ru.util.getBodyDto

class FoodDashboardRepository(
    private val fdRestService: FoodDashboardRestService
) {

    suspend fun loadFoodDashboard(): FoodDashboardState {
        return try {
            val data: List<BaseFoodDashboard> = fdRestService.foodDashboard()
                .getBodyDto().toListFoodModel()
            if (data.isEmpty()) {
                EmptyFoodDashboard
            } else {
                SuccessFoodDashboard(data)
            }
        } catch (throwable: Throwable) {
            ErrorFoodDashboard(throwable)
        }
    }

}