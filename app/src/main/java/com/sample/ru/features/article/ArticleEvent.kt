package com.sample.ru.features.article

sealed interface ArticleEvent
@JvmInline
value class ShowContentArticleEvent(val position: Int): ArticleEvent