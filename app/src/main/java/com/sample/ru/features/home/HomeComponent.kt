package com.sample.ru.features.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.navigation.navigateSafe
import com.sample.ru.R
import com.sample.ru.data.model.FoodModel
import com.sample.ru.data.model.ArticleModel
import com.sample.ru.data.model.MemModel
import com.sample.ru.features.base.BaseSpacer
import com.sample.ru.features.base.LoadError
import com.sample.ru.features.base.LoadProgress
import com.sample.ru.util.TOTAL_ITEM
import com.sample.ru.util.toDate
import timber.log.Timber

class HomeScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.HomeScreen.route) {
            HomeComponent(navController)
        }
    }
}

@Composable
fun HomeComponent(navController: NavController) {
    Timber.tag("--LOG-48").i("-----HomeComponent")
    val scrollState = rememberScrollState()
    val viewModel = hiltViewModel<HomeViewModel>()
    val state: HomeState? by viewModel.state.collectAsState()
    val sideEffect: HomeSideEffect? by viewModel.sideEffect.collectAsState(null)
    ObserveState(
        state,
        scrollState,
        navigateToNews = {
            viewModel.obtainEvent(ClickArticleHomeEvent)
        }, navigateToMemes = {
            viewModel.obtainEvent(ClickMemHomeEvent)
        })
    ObserveSideEffect(sideEffect, navController)
}

@Composable
private fun ObserveState(
    state: HomeState?,
    scrollState: ScrollState,
    navigateToNews: () -> Unit,
    navigateToMemes: () -> Unit
) {
    //TODO
    Timber.tag("--LOG-72").i("-----ObserveState")
    // var loadProgress: Boolean by remember { mutableStateOf(true) }
    state?.let { homeState ->
        when (homeState) {
            is LoadingHome -> {
                //TODO
                Timber.tag("--LOG-77").i("-----LoadingHome")
                LoadProgress()
            }
            is SuccessHome -> {
                //TODO
                Timber.tag("--LOG-81").i("-----SuccessHome")
                HomeUi(scrollState, homeState, navigateToNews, navigateToMemes)
            }
            is ErrorHome -> {
                LoadError()
            }
        }
    }
}

@Composable
private fun ObserveSideEffect(sideEffect: HomeSideEffect?, navController: NavController) {
    sideEffect?.let { homeSideEffect ->
        when (homeSideEffect) {
            is StartMemes -> {
                navController.navigateSafe(Screen.MemesScreen.route)
            }
            is StartNews -> {
                navController.navigateSafe(Screen.ListNewsScreen.route)
            }
        }
    }
}

@Composable
private fun HomeUi(
    scrollState: ScrollState,
    successHome: SuccessHome,
    navigateToNews: () -> Unit,
    navigateToMemes: () -> Unit
) {
    //TODO
    Timber.tag("--LOG-1").i("-----" + successHome.articleModels.size)
    Timber.tag("--LOG-2").i("-----" + successHome.memes.size)
    Timber.tag("--LOG-3").i("-----" + successHome.photoModels.size)
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 68.dp, top = 16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ListNews(successHome.articleModels, navigateToNews)
        ListMemes(successHome.memes, navigateToMemes)
        ListFoods(successHome.foods)
        ThematicList(thematic = successHome.thematic)
        OfferList()
        BaseSpacer()
    }
}

@Composable
private fun ListNews(articleModels: List<ArticleModel>, navigateToNews: () -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .wrapContentSize()
            .horizontalScroll(scrollState)
    ) {
        articleModels.forEach { article ->
            ArticleElement(article, navigateToNews)
        }
    }
}

@Composable
private fun ArticleElement(articleModel: ArticleModel, navigateToNews: () -> Unit) {
    Column(
        modifier = Modifier
            .width(184.dp)
            .wrapContentHeight()
            .padding(8.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(24.dp)
                clip = true
            }
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    navigateToNews.invoke()
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = articleModel.imageUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder)
                        transformations(RoundedCornersTransformation(24f))
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(176.dp)
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(36.dp)
                    .align(Alignment.TopStart)
            )
        }
        Text(
            text = articleModel.newsSite,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Text(
            text = articleModel.publishedAt.toDate(),
            style = MaterialTheme.typography.overline,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 8.dp)
        )
    }
}

@Composable
private fun ListMemes(memes: List<MemModel>, navigateToMemes: () -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .wrapContentSize()
            .horizontalScroll(scrollState)
    ) {
        memes.forEachIndexed { index, memModel ->
            if (index < TOTAL_ITEM) MemElement(memModel, navigateToMemes)
        }
    }
}

@Composable
private fun MemElement(memModel: MemModel, navigateToMemes: () -> Unit) {
    Column(
        modifier = Modifier
            .width(184.dp)
            .wrapContentHeight()
            .padding(8.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(24.dp)
                clip = true
            }
    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Image(
                painter = rememberImagePainter(
                    data = memModel.memUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder)
                        transformations(RoundedCornersTransformation(24f))
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .clickable { navigateToMemes.invoke() }
                    .size(176.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = memModel.author,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 4.dp)
        )
    }
}

@Composable
private fun ListFoods(foods: List<FoodModel>) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .wrapContentSize()
            .horizontalScroll(scrollState)
    ) {
        foods.forEach { foodModel ->
            FoodElement(foodModel)
        }
    }
}

@Composable
private fun FoodElement(foodModel: FoodModel) {
    Box(
        modifier = Modifier
            .size(176.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(24.dp)
                clip = true
            }
    ) {
        Image(
            painter = rememberImagePainter(
                data = foodModel.foodImage,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder)
                    transformations(RoundedCornersTransformation(24f))
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(176.dp)
                .padding(8.dp)
            //contentScale = ContentScale.Inside
            //.clip(RoundedCornerShape(24.dp))
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onPrimary,
            text = foodModel.foodName,
        )
    }
}

@Composable
private fun ThematicList(thematic: List<String>) {
    LazyRow {
        itemsIndexed(thematic) { _, item ->
            ThematicElement(item)
        }
    }
}

@Composable
private fun ThematicElement(thematicTitle: String) {
    Button(
        modifier = Modifier.padding(8.dp),
        elevation = ButtonDefaults.elevation(2.dp),
        shape = RoundedCornerShape(36.dp),
        border = BorderStroke(width = 2.dp, brush = SolidColor(Color.Gray)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
        onClick = {

        }
    ) {
        Text(
            text = thematicTitle,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onPrimary,
        )
    }
}

@Composable
private fun OfferList() {
    repeat(3) {
        OfferElement()
    }
}


@Composable
private fun OfferElement() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp)
    ) {
        val (logo, title, description, image) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_gallery),
            contentDescription = "Espresso",
            modifier = Modifier
                .size(100.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                },
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
        )
        Text(
            text = "Espresso",
            fontSize = 24.sp,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                linkTo(start = logo.end, end = parent.end, startMargin = 8.dp, endMargin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = "Espresso is coffee of Italian origin, brewed by forcing a small amount of nearly boiling water under pressure (expressing) through finely-ground coffee beans.",
            style = TextStyle(textAlign = TextAlign.Justify),
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.constrainAs(description) {
                top.linkTo(title.bottom)
                linkTo(start = title.start, end = image.start, endMargin = 24.dp)
                width = Dimension.fillToConstraints
            }
        )
        Image(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 16.dp)
        )
    }
}