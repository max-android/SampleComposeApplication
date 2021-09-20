package com.sample.ru.features.mem

sealed interface MemEvent
@JvmInline
value class ShowContentMemEvent(val position: Int): MemEvent