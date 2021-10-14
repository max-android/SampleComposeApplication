package com.sample.ru.data.mapper

import com.sample.ru.data.model.FoodBody
import com.sample.ru.data.model.FoodDashboardModel
import com.sample.ru.features.foodDashboard.BaseFoodDashboard
import com.sample.ru.features.foodDashboard.DetailFoodDashboard
import com.sample.ru.features.foodDashboard.HeaderFoodDashboard

fun FoodDashboardModel.toListFoodModel(): List<BaseFoodDashboard> {
    val foods = mutableListOf<BaseFoodDashboard>()
    this.data.forEach { food ->
        foods.add(HeaderFoodDashboard(food.foodHeader?.title.orEmpty()))
        food.data.forEach { foodBody ->
            foods.add(foodBody.toDetailFoodDashboard())
        }
    }
    return foods
}

fun FoodBody.toDetailFoodDashboard(): DetailFoodDashboard {
    return DetailFoodDashboard(
        image = imageUrl,
        title = title,
        subTitle = subTitle.orEmpty(),
        rating = foodMetaData?.rating.orEmpty(),
        reviewCount = foodMetaData?.reviewCount.orEmpty(),
        hasFreeDelivery = foodMetaData?.hasFreeDelivery ?: false
    )
}