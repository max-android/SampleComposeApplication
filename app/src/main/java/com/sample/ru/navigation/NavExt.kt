package com.sample.ru.navigation

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.*

/**
 * Позволяет осуществлять переход по навигации в безопасном режиме и
 * предотвращает краш при открытии более чем одного экрана (мультитач нажатии).
 */
fun NavController.navigateSafe(
    route: String,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    if (currentDestination?.route != route) {
        navigate(route, navOptions, navExtras)
    }
}

fun NavController.navigateSafeWithBuilder(
    route: String,
    builder: NavOptionsBuilder.() -> Unit
) {
    if (currentDestination?.route != route) {
        navigate(route, builder)
    }
}

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    // Safely update the current `onBack` lambda when a new one is provided
    val currentOnBack by rememberUpdatedState(onBack)
    // Remember in Composition a back callback that calls the `onBack` lambda
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }
    // On every successful composition, update the callback with the `enabled` value
    SideEffect {
        backCallback.isEnabled = enabled
    }
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        // Add callback to the backDispatcher
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        // When the effect leaves the Composition, remove the callback
        onDispose {
            backCallback.remove()
        }
    }
}

fun NavDestination.updateSelectedTab(index: Int, tabBarItem: BottomNavItem): Boolean {
    return route == tabBarItem.screen.route ||
            route == Screen.ListNewsScreen.route && index == 0 ||
            //TODO
           // route == Screen.ArticleScreen.route && index == 0 ||
            route == Screen.MemesScreen.route && index == 0
}

fun isNeedShowBottomBar(navBackStackEntry: NavBackStackEntry?): Boolean {
    val currentDestination = navBackStackEntry?.destination?.route
    if (currentDestination != null) {
        return currentDestination.contains(Screen.HomeScreen.route) ||
                currentDestination.contains(Screen.GalleryScreen.route) ||
                currentDestination.contains(Screen.ProfileScreen.route) ||
                currentDestination.contains(Screen.ListNewsScreen.route) ||
                //TODO
               // currentDestination.contains(Screen.ArticleScreen.route) ||
                currentDestination.contains(Screen.MemesScreen.route)
    }
    return false
}