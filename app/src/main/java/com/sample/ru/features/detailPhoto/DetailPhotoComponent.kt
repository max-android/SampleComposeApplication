package com.sample.ru.features.detailPhoto

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.sample.ru.R
import com.sample.ru.data.model.PhotoModel
import com.sample.ru.features.base.EmptyDetailItemUi
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.KEY_PHOTO_ID
import com.sample.ru.navigation.Screen
import com.sample.ru.ui.theme.Purple700
import com.sample.ru.util.DEFAULT_POSITION
import com.sample.ru.util.composeContext

class DetailPhotoScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(
            route = Screen.DetailImageScreen.route + "/{$KEY_PHOTO_ID}",
            arguments = listOf(
                navArgument(KEY_PHOTO_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            DetailImageComponent(it)
        }
    }

}

@Composable
private fun DetailImageComponent(backStackEntry: NavBackStackEntry) {
    val viewModel = hiltViewModel<DetailPhotoViewModel>()
    val position = backStackEntry.arguments?.getInt(KEY_PHOTO_ID) ?: DEFAULT_POSITION
    if (!viewModel.isShowContent) {
        viewModel.obtainEvent(ShowContentDetailPhotoEvent(position))
    }
    val state: DetailPhotoState? by viewModel.state.collectAsState()
    ObserveState(state)
}

@Composable
private fun ObserveState(state: DetailPhotoState?) {
    state?.let { photoState ->
        when (photoState) {
            is SuccessDetailPhoto -> {
                PhotoDetailUi(photoState.photo)
            }
            is EmptyDetailPhoto -> {
                EmptyDetailItemUi()
            }
        }
    }
}

@Composable
private fun PhotoDetailUi(photo: PhotoModel) {
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .padding(8.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = photo.downloadUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder)
                        transformations(RoundedCornersTransformation(24f))
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(600.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                text = composeContext().getString(R.string.gallery_created, photo.author),
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
                    .clickable {

                    },
                style = MaterialTheme.typography.caption,
                color = Purple700,
                text = photo.url,
            )
        }
    }
}