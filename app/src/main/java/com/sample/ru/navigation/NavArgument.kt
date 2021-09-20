package com.sample.ru.navigation

sealed class NavArgument(val key: String) {
    object FromListArg: NavArgument(KEY_ARTICLE_ID)
    object FromScreenArg: NavArgument(KEY_FROM_SCREEN)
}
