package com.sample.ru.features.photoWebView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.sample.ru.features.base.ComposeWebView
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.KEY_PHOTO_WEB_LINK
import com.sample.ru.navigation.Screen

class PhotoWebViewScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(
            route = Screen.PhotoWebViewScreen.route + "/{$KEY_PHOTO_WEB_LINK}",
            arguments = listOf(
                navArgument(KEY_PHOTO_WEB_LINK) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            PhotoWebViewComponent(backStackEntry)
        }
    }

}

@Composable
private fun PhotoWebViewComponent(backStackEntry: NavBackStackEntry) {
    val viewModel = hiltViewModel<PhotoWebViewViewModel>()
    val webLink = backStackEntry.arguments?.getString(KEY_PHOTO_WEB_LINK)
    webLink?.let { url ->
        viewModel.obtainEvent(ShowContentPhotoWebView(url))
    }
    val state: PhotoWebViewState? by viewModel.state.collectAsState()
    ObserveState(state)
}

@Composable
private fun ObserveState(state: PhotoWebViewState?) {
    state?.let { webState ->
        when (webState) {
            is SuccessPhotoWebView -> {
                ComposeWebView(url = webState.webLink)
            }
        }
    }
}