package com.sample.ru.data.network

import com.sample.ru.data.model.MemListModel
import retrofit2.Response
import retrofit2.http.GET

interface MemesRestService {
    @GET("memes/")
    suspend fun memes(): Response<MemListModel>
}