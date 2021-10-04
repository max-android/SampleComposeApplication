package com.sample.ru.data.network

import com.sample.ru.data.model.FoodDashboardModel
import retrofit2.Response
import retrofit2.http.GET

interface FoodDashboardRestService {
    @GET("data.json")
    suspend fun foodDashboard(): Response<FoodDashboardModel>
}