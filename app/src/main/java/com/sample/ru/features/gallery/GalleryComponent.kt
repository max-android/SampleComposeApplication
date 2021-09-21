package com.sample.ru.features.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import com.sample.ru.R
import com.sample.ru.data.model.BaseModel
import com.sample.ru.data.model.PhotoModel
import com.sample.ru.features.base.EmptyListUi
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.navigation.navigateSafe

class GalleryScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.GalleryScreen.route) {
            GalleryComponent(navController)
        }
    }
}

@Composable
fun GalleryComponent(navController: NavController) {
    val viewModel = hiltViewModel<GalleryViewModel>()
    val state: GalleryState? by viewModel.state.collectAsState()
    val sideEffect: GallerySideEffect? by viewModel.sideEffect.collectAsState(null)
    ObserveState(
        state,
        navigateToPhoto = { position ->
            viewModel.obtainEvent(ClickItemGalleryEvent(position))
        })
    ObserveSideEffect(sideEffect, navController)
}

@Composable
private fun ObserveState(state: GalleryState?, navigateToPhoto: (Int) -> Unit) {
    state?.let { galleryState ->
        when (galleryState) {
            is SuccessGallery -> {
                GalleryUi(galleryState.photos, navigateToPhoto)
            }
            is EmptyGallery -> {
                EmptyListUi()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GalleryUi(listPhotos: List<BaseModel>, navigateToPhoto: (Int) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 68.dp)
    ) {
        itemsIndexed(listPhotos) { index, item ->
            if (item is PhotoModel) {
                PhotoItem(item, index, navigateToPhoto)
            }
        }
    }
}

@Composable
private fun PhotoItem(photo: PhotoModel, photoPosition: Int, click: (Int) -> Unit) {
    Image(
        painter = rememberImagePainter(
            data = photo.downloadUrl,
            builder = {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder)
            }
        ),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
            .graphicsLayer {
                clip = true
            }
            .clickable { click.invoke(photoPosition) }
    )
}

@Composable
private fun ObserveSideEffect(sideEffect: GallerySideEffect?, navController: NavController) {
    sideEffect?.let { photoSideEffect ->
        when (photoSideEffect) {
            is ShowPhoto -> {
                navController.navigateSafe("${Screen.DetailImageScreen.route}/${photoSideEffect.position}")
            }
        }
    }
}