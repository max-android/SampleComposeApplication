package com.sample.ru.features.foodDashboard

interface BaseFoodDashboard

@JvmInline
value class HeaderFoodDashboard(val header: String) : BaseFoodDashboard
data class DetailFoodDashboard(
    val image: String,
    val title: String,
    val subTitle: String,
    val rating: String,
    val reviewCount: String,
    val hasFreeDelivery: Boolean
) : BaseFoodDashboard