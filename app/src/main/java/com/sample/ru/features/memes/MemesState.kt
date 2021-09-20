package com.sample.ru.features.memes

import com.sample.ru.data.model.BaseModel

sealed class MemesState
class SuccessMemes(
    val memes: List<BaseModel>
) : MemesState()
object EmptyMemes : MemesState()