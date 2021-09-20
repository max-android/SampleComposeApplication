package com.sample.ru.features.news

sealed interface ListNewsEvent
object ShowContentListNewsEvent: ListNewsEvent
@JvmInline
value class ClickArticleEvent(val position: Int): ListNewsEvent