package com.sample.ru.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("download_url")
    val downloadUrl: String
)