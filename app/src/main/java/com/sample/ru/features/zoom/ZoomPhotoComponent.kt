package com.sample.ru.features.zoom

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import com.sample.ru.features.base.ZoomableComposable
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.KEY_PHOTO_URL
import com.sample.ru.navigation.Screen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.navigation.*

class ZoomScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(
            route = Screen.ZoomImageScreen.route + "/{$KEY_PHOTO_URL}",
            arguments = listOf(
                navArgument(KEY_PHOTO_URL) {
                    type = NavType.StringType
                }
            )
        ) {
            ZoomPhotoComponent(it)
        }
    }

    @Composable
    private fun ZoomPhotoComponent(backStackEntry: NavBackStackEntry) {
        val viewModel = hiltViewModel<ZoomPhotoViewModel>()
        val imageUrl = backStackEntry.arguments?.getString(KEY_PHOTO_URL)
        viewModel.obtainEvent(ShowContentZoomPhoto(imageUrl))
        val state: ZoomPhotoState? by viewModel.state.collectAsState()
        ObserveState(state)
    }

    @Composable
    private fun ObserveState(state: ZoomPhotoState?) {
        state?.let { zoomPhotoState ->
            when (zoomPhotoState) {
                is SuccessZoomPhoto -> {
                    ZoomableComposable(downloadUrl = zoomPhotoState.photoUrl)
                }
            }
        }
    }
}