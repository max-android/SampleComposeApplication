package com.sample.ru.data.service

import androidx.collection.arrayMapOf
import com.sample.ru.data.model.ArticleModel
import com.sample.ru.data.model.BaseModel
import com.sample.ru.data.model.MemModel
import com.sample.ru.data.model.PhotoModel

class CacheService {

    private val cache = arrayMapOf<String, List<BaseModel>>()

    fun createCache(
        listMemes: List<MemModel>,
        listNews: List<ArticleModel>,
        listPhotos: List<PhotoModel>
    ) {
        cache[MEM_CACHE] = listMemes
        cache[ARTICLE_CACHE] = listNews
        cache[PHOTOS_CACHE] = listPhotos
    }

    fun memesCache(): List<BaseModel> {
        return cache[MEM_CACHE] ?: listOf()
    }

    fun articlesCache(): List<BaseModel> {
        return cache[ARTICLE_CACHE] ?: listOf()
    }

    fun photosCache(): List<BaseModel> {
        return cache[PHOTOS_CACHE] ?: listOf()
    }

    companion object {
        private const val MEM_CACHE = "mem_cache"
        private const val ARTICLE_CACHE = "article_cache"
        private const val PHOTOS_CACHE = "photos_cache"
    }
}