package com.sample.ru.data.repository

import com.sample.ru.data.service.CacheService
import com.sample.ru.features.memes.EmptyMemes
import com.sample.ru.features.memes.MemesState
import com.sample.ru.features.memes.SuccessMemes

class MemesRepository(private val cacheService: CacheService) {

    fun memes(): MemesState = if (cacheService.memesCache().isEmpty()) {
        EmptyMemes
    } else {
        SuccessMemes(cacheService.memesCache())
    }

}