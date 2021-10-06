package com.sample.ru.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.ru.features.gallery.GalleryScreenFactory
import com.sample.ru.features.home.HomeScreenFactory
import com.sample.ru.features.mem.MemScreenFactory
import com.sample.ru.features.memes.MemesScreenFactory
import com.sample.ru.features.news.ListNewsScreenFactory
import com.sample.ru.features.article.ArticleScreenFactory
import com.sample.ru.features.detailPhoto.DetailPhotoScreenFactory
import com.sample.ru.features.listPhoneImage.ListPhoneImageScreenFactory
import com.sample.ru.features.phoneImage.PhoneImageScreenFactory
import com.sample.ru.features.photoWebView.PhotoWebViewScreenFactory
import com.sample.ru.features.profile.ProfileScreenFactory
import com.sample.ru.features.sliderPhoto.SliderPhotoScreenFactory
import com.sample.ru.features.zoom.ZoomScreenFactory
import com.sample.ru.navigation.*

@Composable
fun MainComponent(onDarkModeChanged: (Boolean) -> Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    //val viewModel = hiltViewModel<MainViewModel>()

    Scaffold(
        bottomBar = {
            if (isNeedShowBottomBar(navBackStackEntry)) {
                BottomNavigation {
                    bottomNavItems.forEachIndexed { index, tabBarItem ->
                        BottomNavigationItem(
                            modifier = Modifier.background(color = MaterialTheme.colors.secondaryVariant),
                            icon = {
                                Icon(
                                    painter = painterResource(id = tabBarItem.drawableResId),
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    stringResource(tabBarItem.titleResId),
                                    color = MaterialTheme.colors.onPrimary
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { destination ->
                                destination.updateSelectedTab(index, tabBarItem)
                            } == true,
                            selectedContentColor = Color.White,
                            unselectedContentColor = MaterialTheme.colors.primaryVariant,
                            onClick = {
                                navController.navigateSafeWithBuilder(tabBarItem.screen.route) {
//                                        // Pop up to the start destination of the graph to
//                                        // avoid building up a large stack of destinations
//                                        // on the back stack as users select items
//                                        popUpTo(navController.graph.findStartDestination().id) {
//                                            saveState = true
//                                        }
                                    //popUpTo(navController.graph.findStartDestination().id)
                                    popUpTo(Screen.HomeScreen.route) {
                                        saveState = true
                                    }
//                                        // Avoid multiple copies of the same destination when
//                                        // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    // restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        },
        content = { _ ->
            NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
                HomeScreenFactory().create(this, navController)
                GalleryScreenFactory().create(this, navController)
                ProfileScreenFactory(onDarkModeChanged).create(this, navController)
                ListNewsScreenFactory().create(this, navController)
                ArticleScreenFactory().create(this, navController)
                MemesScreenFactory().create(this, navController)
                MemScreenFactory().create(this, navController)
                DetailPhotoScreenFactory().create(this, navController)
                ZoomScreenFactory().create(this, navController)
                SliderPhotoScreenFactory().create(this, navController)
                PhotoWebViewScreenFactory().create(this, navController)
                PhoneImageScreenFactory().create(this, navController)
                ListPhoneImageScreenFactory().create(this, navController)
            }
        }
    )

}