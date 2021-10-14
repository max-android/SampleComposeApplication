package com.sample.ru.data.model

import com.google.gson.annotations.SerializedName

data class FoodDashboardModel(
    @SerializedName("data")
    val data: List<FoodDashboardBaseModel>
)

data class FoodDashboardBaseModel(
    @SerializedName("header")
    val foodHeader: FoodHeader?,
    @SerializedName("data")
    val data: List<FoodBody>
)

data class FoodHeader(
    @SerializedName("title")
    val title: String
)

data class FoodBody(
    @SerializedName("title")
    val title: String,
    @SerializedName("subTitle")
    val subTitle: String?,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("meta")
    val foodMetaData: FoodMetaData?
)

data class FoodMetaData(
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("reviewCount")
    val reviewCount: String?,
    @SerializedName("hasFreeDelivery")
    val hasFreeDelivery: Boolean?
)