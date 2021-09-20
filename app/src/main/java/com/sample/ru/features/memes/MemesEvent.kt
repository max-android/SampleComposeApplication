package com.sample.ru.features.memes

sealed class MemesEvent
object ShowContentMemesEvent: MemesEvent()
class ClickMemEvent(val position: Int): MemesEvent()