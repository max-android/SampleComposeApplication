package com.sample.ru.data.repository

import com.sample.ru.data.model.ArticleModel
import com.sample.ru.data.service.CacheService
import com.sample.ru.features.article.ArticleState
import com.sample.ru.features.article.EmptyArticle
import com.sample.ru.features.article.SuccessArticle
import com.sample.ru.features.news.EmptyListNews
import com.sample.ru.features.news.ListNewsState
import com.sample.ru.features.news.SuccessListNews

class ListNewsRepository(private val cacheService: CacheService) {

    private fun articlesCache() = cacheService.articlesCache()

    fun news(): ListNewsState = if (articlesCache().isEmpty()) {
        EmptyListNews
    } else {
        SuccessListNews(articlesCache())
    }

    fun article(item: Int): ArticleState {
        val articleModel = articlesCache().getOrNull(item)
        return if (articleModel != null && articleModel is ArticleModel) {
            SuccessArticle(articleModel)
        } else {
            EmptyArticle
        }
    }

}