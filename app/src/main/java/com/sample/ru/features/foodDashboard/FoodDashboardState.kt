package com.sample.ru.features.foodDashboard

sealed class FoodDashboardState
object LoadingFoodDashboard: FoodDashboardState()
class SuccessFoodDashboard(
    val foods: List<BaseFoodDashboard>
) : FoodDashboardState()
object EmptyFoodDashboard : FoodDashboardState()
class ErrorFoodDashboard(val error: Throwable) : FoodDashboardState()