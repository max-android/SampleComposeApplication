package com.sample.ru.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen(NavRoute.HOME_ROUTE)
    object GalleryScreen: Screen(NavRoute.GALLERY_ROUTE)
    object ProfileScreen: Screen(NavRoute.PROFILE_ROUTE)
    object ListNewsScreen: Screen(NavRoute.LIST_NEWS_ROUTE)
    object ArticleScreen: Screen(NavRoute.ARTICLE_ROUTE)
    object MemesScreen: Screen(NavRoute.MEMES_ROUTE)
    object MemScreen: Screen(NavRoute.MEM_ROUTE)
    object DetailImageScreen: Screen(NavRoute.DETAIL_IMAGE_ROUTE)
    object ZoomImageScreen: Screen(NavRoute.ZOOM_IMAGE_ROUTE)
    object SliderPhotoScreen: Screen(NavRoute.SLIDER_PHOTO_ROUTE)
    object PhotoWebViewScreen: Screen(NavRoute.PHOTO_WEB_VIEW_ROUTE)
    object PhoneImageScreen: Screen(NavRoute.PHONE_IMAGE_ROUTE)
    object ListPhoneImageScreen: Screen(NavRoute.LIST_PHONE_IMAGE_ROUTE)
    object FoodDashboardScreen: Screen(NavRoute.FOOD_DASHBOARD_ROUTE)
}