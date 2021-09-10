package com.sample.ru.features.home

sealed class HomeEvent
object ShowContentHomeEvent: HomeEvent()
object ClickArticleHomeEvent: HomeEvent()
object ClickMemHomeEvent: HomeEvent()