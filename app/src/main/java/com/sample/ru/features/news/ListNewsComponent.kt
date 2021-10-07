package com.sample.ru.features.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.sample.ru.R
import com.sample.ru.data.model.ArticleModel
import com.sample.ru.data.model.BaseModel
import com.sample.ru.features.base.EmptyListUi
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.navigation.navigateSafe
import com.sample.ru.util.composeContext
import com.sample.ru.util.toDate

class ListNewsScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.ListNewsScreen.route) {
            ListNewsComponent(navController)
        }
    }

}

@Composable
fun ListNewsComponent(navController: NavController) {
    val viewModel = hiltViewModel<ListNewsViewModel>()
    val state: ListNewsState? by viewModel.state.collectAsState()
    val sideEffect: ListNewsSideEffect? by viewModel.sideEffect.collectAsState(null)
    ObserveState(
        state,
        navigateToArticle = { position ->
            viewModel.obtainEvent(ClickArticleEvent(position))
        })
    ObserveSideEffect(sideEffect, navController)
}

@Composable
private fun ObserveState(state: ListNewsState?, navigateToArticle: (Int) -> Unit) {
    state?.let { newsState ->
        when (newsState) {
            is SuccessListNews -> {
                NewsUi(newsState.news, navigateToArticle)
            }
            is EmptyListNews -> {
                EmptyListUi()
            }
        }
    }
}

@Composable
private fun NewsUi(listNews: List<BaseModel>, navigateToArticle: (Int) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 8.dp, end = 8.dp, top = 16.dp, bottom = 68.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(listNews) { index, item ->
            if (item is ArticleModel) {
                ArticleItem(item, index, navigateToArticle)
            }
        }
    }
}

@Composable
private fun ArticleItem(article: ArticleModel, articlePosition: Int, click: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable {
                click.invoke(articlePosition)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Column() {
            Image(
                painter = rememberImagePainter(
                    data = article.imageUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder)
                        transformations(RoundedCornersTransformation(24f))
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(275.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary,
                text = article.title,
            )
            Text(
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onPrimary,
                text = composeContext().getString(
                    R.string.new_created, article.publishedAt.toDate(), article.newsSite
                ),
            )
        }
    }
}

@Composable
private fun ObserveSideEffect(sideEffect: ListNewsSideEffect?, navController: NavController) {
    sideEffect?.let { newsSideEffect ->
        when (newsSideEffect) {
            is StartArticle -> {
                navController.navigateSafe("${Screen.ArticleScreen.route}/${newsSideEffect.position}")
            }
        }
    }
}