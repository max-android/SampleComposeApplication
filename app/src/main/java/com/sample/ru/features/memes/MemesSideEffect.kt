package com.sample.ru.features.memes

sealed interface MemesSideEffect
@JvmInline
value class StartMem(val position: Int): MemesSideEffect