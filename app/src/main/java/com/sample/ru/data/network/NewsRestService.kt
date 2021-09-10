package com.sample.ru.data.network

import com.sample.ru.data.model.ArticleModel
import retrofit2.Response
import retrofit2.http.GET

interface NewsRestService {
    @GET("ARTICLES")
    suspend fun articles(): Response<List<ArticleModel>>
}