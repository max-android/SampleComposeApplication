package com.sample.ru.features.main

sealed interface MainState
@JvmInline
value class SuccessMainState(val isDarkTheme: Boolean): MainState