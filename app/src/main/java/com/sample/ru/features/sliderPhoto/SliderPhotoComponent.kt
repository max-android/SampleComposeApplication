package com.sample.ru.features.sliderPhoto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sample.ru.R
import com.sample.ru.data.model.BaseModel
import com.sample.ru.data.model.PhotoModel
import com.sample.ru.features.base.EmptyListUi
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.util.SLIDER_IMAGES_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

class SliderPhotoScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.SliderPhotoScreen.route) {
            SliderPhotoComponent()
        }
    }

    @Composable
    fun SliderPhotoComponent() {
        val viewModel = hiltViewModel<SliderPhotoViewModel>()
        val state: SliderPhotoState? by viewModel.state.collectAsState()
        ObserveState(state)
    }

    @Composable
    private fun ObserveState(state: SliderPhotoState?) {
        state?.let { sliderState ->
            when (sliderState) {
                is SuccessSliderPhoto -> {
                    SliderPhotoUi(sliderState.photos)
                }
                is EmptySliderPhoto -> {
                    EmptyListUi()
                }
            }
        }
    }


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun SliderPhotoUi(listPhotos: List<BaseModel>) {

        val pagerState = rememberPagerState()

        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(SLIDER_IMAGES_DELAY)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                    //animationSpec = tween(600)
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            count = listPhotos.size
        ) { page: Int ->
            val item = listPhotos[page]
            if (item is PhotoModel) {
                SliderImage(imageUrl = item.downloadUrl)
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun SliderImage(imageUrl: String) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder)
                    scale(Scale.FILL)
                    transformations(
                        //BlurTransformation(composeContext()),
//                        GrayscaleTransformation(),
//                        CircleCropTransformation()
                    )
                }
            ),
            contentDescription = "slider image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

}