package com.sample.ru.features.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
        },
        navigateToSlider = {
            viewModel.obtainEvent(ClickSliderPhotoEvent)
        }
    )
    ObserveSideEffect(sideEffect, navController)
}

@Composable
private fun ObserveState(
    state: GalleryState?, navigateToPhoto: (Int) -> Unit, navigateToSlider: () -> Unit
) {
    state?.let { galleryState ->
        when (galleryState) {
            is SuccessGallery -> {
                GalleryUi(galleryState.photos, navigateToPhoto, navigateToSlider)
            }
            is EmptyGallery -> {
                EmptyListUi()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GalleryUi(
    listPhotos: List<BaseModel>,
    navigateToPhoto: (Int) -> Unit,
    navigateToSlider: () -> Unit
) {
    ConstraintLayout() {
        val (sliderButton, contentGallery) = createRefs()

        LazyVerticalGrid(
            modifier = Modifier
                .constrainAs(contentGallery) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                },
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 120.dp)
        ) {
            itemsIndexed(listPhotos) { index, item ->
                if (item is PhotoModel) {
                    PhotoItem(item, index, navigateToPhoto)
                }
            }
        }

        Button(
            modifier = Modifier
                .wrapContentWidth()
                .height(56.dp)
                .constrainAs(sliderButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 68.dp)
                },
            elevation = ButtonDefaults.elevation(defaultElevation = 6.dp),
            onClick = navigateToSlider,
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            )
        ) {
            Text(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                text = stringResource(id = R.string.gallery_show_slider),
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onPrimary
            )
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
            is ShowSliderPhoto -> {
                navController.navigateSafe(Screen.SliderPhotoScreen.route)
            }
        }
    }
}