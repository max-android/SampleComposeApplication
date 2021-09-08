package com.sample.ru.features.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
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
import com.sample.ru.data.FoodModel
import com.sample.ru.data.MemModel
import com.sample.ru.data.homeImages
import com.sample.ru.data.thematic
import androidx.compose.runtime.getValue

class HomeScreenFactory : ComposeNavFactory {

    @ExperimentalFoundationApi
    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.HomeScreen.route) {
            HomeComponent(
                navigateToNews = { navController.navigateSafe(Screen.ListNewsScreen.route) },
                navigateToMemes = { navController.navigateSafe(Screen.MemesScreen.route) }
            )
        }
    }

}

@ExperimentalFoundationApi
@Composable
fun HomeComponent(navigateToNews: () -> Unit, navigateToMemes: () -> Unit) {
    val scrollState = rememberScrollState()
    val viewModel = hiltViewModel<HomeViewModel>()
    val state: HomeState by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ListFood(homeImages)
        Text(text = "Home Screen")
        Button(onClick = { navigateToNews.invoke() }) {
            Text(text = "GO TO List News")
        }
        Button(onClick = { navigateToMemes.invoke() }) {
            Text(text = "GO TO Memes")
        }
        ThematicList(thematic = thematic)
    }
}

@Composable
fun ListFood(foods: List<FoodModel>) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .wrapContentSize()
            .horizontalScroll(scrollState)
    ) {
        foods.forEach { foodModel ->
            Box(
                modifier = Modifier.size(176.dp)
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
                    text = foodModel.foodName,
                )
            }
        }
    }
}


@Composable
fun ListMemes(memes: List<MemModel>) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .clickable(onClick = {

            })
            .padding(top = 24.dp)
            .wrapContentSize()
            .horizontalScroll(scrollState)
    ) {
        memes.forEach { memModel ->
            Image(
                painter = rememberImagePainter(
                    data = memModel.memImage,
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
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ThematicList(thematic: List<String>) {
    LazyRow {
        itemsIndexed(thematic) { _, item ->
            ThematicElement(item)
        }
    }
}

@Composable
fun ThematicElement(thematicTitle: String) {
    Button(
        onClick = {

        },
        modifier = Modifier.padding(start = 8.dp),
        elevation = ButtonDefaults.elevation(2.dp),
        shape = RoundedCornerShape(36.dp),
        border = BorderStroke(width = 2.dp, brush = SolidColor(Color.Gray)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
    ) {
        Text(text = thematicTitle, modifier = Modifier.padding(0.dp))
    }
}
