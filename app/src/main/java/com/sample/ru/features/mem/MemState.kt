package com.sample.ru.features.mem

import com.sample.ru.data.model.MemModel

sealed class MemState
class SuccessMem(
    val mem: MemModel
) : MemState()
object EmptyMem : MemState()