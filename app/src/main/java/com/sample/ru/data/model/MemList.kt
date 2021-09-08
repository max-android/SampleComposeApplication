package com.sample.ru.data.model

import com.google.gson.annotations.SerializedName

class MemList(
    @SerializedName("data")
    val memes: List<Mem>
)