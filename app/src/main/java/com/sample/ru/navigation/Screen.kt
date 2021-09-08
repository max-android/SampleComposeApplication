package com.sample.ru.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen(NavRoute.HOME_ROUTE)
    object GalleryScreen: Screen(NavRoute.GALLERY_ROUTE)
    object ProfileScreen: Screen(NavRoute.PROFILE_ROUTE)
    object ListNewsScreen: Screen(NavRoute.LIST_NEWS_ROUTE)
    object DetailNewsScreen: Screen(NavRoute.DETAIL_NEWS_ROUTE)
    object MemesScreen: Screen(NavRoute.MEMES_ROUTE)
    object DetailImageScreen: Screen(NavRoute.DETAIL_IMAGE_ROUTE)
}