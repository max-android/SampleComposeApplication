package com.sample.ru.data.network

import com.sample.ru.data.model.Photo
import retrofit2.Response
import retrofit2.http.GET

interface PhotoRestService {
    @GET("v2/list")
    suspend fun photos(): Response<List<Photo>>
}