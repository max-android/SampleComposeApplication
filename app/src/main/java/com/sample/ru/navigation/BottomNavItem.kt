package com.sample.ru.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sample.ru.R

data class BottomNavItem(
    val screen: Screen,
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.HomeScreen,
        titleResId = R.string.home,
        drawableResId = R.drawable.ic_home
    ),
    BottomNavItem(
        screen = Screen.GalleryScreen,
        titleResId = R.string.gallery,
        drawableResId = R.drawable.ic_gallery
    ),
    BottomNavItem(
        screen = Screen.ProfileScreen,
        titleResId = R.string.profile,
        drawableResId = R.drawable.ic_profile
    )
)