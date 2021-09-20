package com.sample.ru.features.main

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.sample.ru.features.profile.ProfileScreenFactory
import com.sample.ru.navigation.*
import com.sample.ru.ui.theme.Purple700

@Composable
fun MainComponent() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    //val viewModel = hiltViewModel<MainViewModel>()

    Scaffold(
        bottomBar = {
            val currentDestination = navBackStackEntry?.destination
            if (isNeedShowBottomBar(navBackStackEntry)) {
                BottomNavigation {
                    bottomNavItems.forEachIndexed { index, tabBarItem ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = tabBarItem.drawableResId),
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = { Text(stringResource(tabBarItem.titleResId)) },
                            selected = currentDestination?.hierarchy?.any { destination ->
                                destination.updateSelectedTab(index, tabBarItem)
                            } == true,
                            unselectedContentColor = Purple700.copy(alpha = 0.5f),
                            onClick = {
                                navController.navigateSafeWithBuilder(tabBarItem.screen.route) {
//                                        // Pop up to the start destination of the graph to
//                                        // avoid building up a large stack of destinations
//                                        // on the back stack as users select items
//                                        popUpTo(navController.graph.findStartDestination().id) {
//                                            saveState = true
//                                        }
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
                ProfileScreenFactory().create(this, navController)
                ListNewsScreenFactory().create(this, navController)
                ArticleScreenFactory().create(this, navController)
                MemesScreenFactory().create(this, navController)
                MemScreenFactory().create(this, navController)
                DetailPhotoScreenFactory().create(this, navController)
            }
        }
    )

}