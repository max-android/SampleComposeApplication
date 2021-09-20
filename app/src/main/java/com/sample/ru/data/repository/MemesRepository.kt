package com.sample.ru.data.repository

import com.sample.ru.data.model.MemModel
import com.sample.ru.data.service.CacheService
import com.sample.ru.features.mem.EmptyMem
import com.sample.ru.features.mem.MemState
import com.sample.ru.features.mem.SuccessMem
import com.sample.ru.features.memes.EmptyMemes
import com.sample.ru.features.memes.MemesState
import com.sample.ru.features.memes.SuccessMemes

class MemesRepository(private val cacheService: CacheService) {

    fun memes(): MemesState = if (cacheService.memesCache().isEmpty()) {
        EmptyMemes
    } else {
        SuccessMemes(cacheService.memesCache())
    }

    fun mem(item: Int): MemState {
        val memModel = cacheService.memesCache().getOrNull(item)
        return if (memModel != null && memModel is MemModel) {
            SuccessMem(memModel)
        } else {
            EmptyMem
        }
    }

}