package com.sample.ru.data.service

import androidx.collection.arrayMapOf
import com.sample.ru.data.model.ArticleModel
import com.sample.ru.data.model.BaseModel
import com.sample.ru.data.model.MemModel

class CacheService {

    private val memes = arrayMapOf<String, List<BaseModel>>()

    fun createCache(listMemes: List<MemModel>, listNews: List<ArticleModel>) {
        memes[MEM_CACHE] = listMemes
        memes[ARTICLE_CACHE] = listNews
    }

    fun memesCache(): List<BaseModel> {
        return memes[MEM_CACHE] ?: listOf()
    }

    fun articlesCache(): List<BaseModel> {
        return memes[ARTICLE_CACHE] ?: listOf()
    }

    companion object {
        private const val MEM_CACHE = "mem_cache"
        private const val ARTICLE_CACHE = "article_cache"
    }
}