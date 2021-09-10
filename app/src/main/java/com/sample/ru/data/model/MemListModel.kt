package com.sample.ru.data.model

import com.google.gson.annotations.SerializedName

class MemListModel(
    @SerializedName("data")
    val memes: List<MemModel>
)