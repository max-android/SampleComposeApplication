package com.sample.ru.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("url")
    val webUrl: String,
    @SerializedName("newsSite")
    val newsSite: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
)