package com.sample.ru.data.model

import com.google.gson.annotations.SerializedName

data class MemModel(
    @SerializedName("submission_id")
    val id: String,
    @SerializedName("submission_url")
    val memUrl: String,
    @SerializedName("submission_title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("created")
    val created: String
): BaseModel