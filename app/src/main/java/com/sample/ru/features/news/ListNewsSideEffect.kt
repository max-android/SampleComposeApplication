package com.sample.ru.features.news

sealed interface ListNewsSideEffect
@JvmInline
value class StartArticle(val position: Int): ListNewsSideEffect