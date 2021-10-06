package com.sample.ru.features.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.sample.ru.R
import com.sample.ru.data.model.ArticleModel
import com.sample.ru.features.base.EmptyDetailItemUi
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.KEY_ARTICLE_ID
import com.sample.ru.navigation.Screen
import com.sample.ru.util.DEFAULT_POSITION
import com.sample.ru.util.composeContext
import com.sample.ru.util.toDate

class ArticleScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(
            route = Screen.ArticleScreen.route + "/{$KEY_ARTICLE_ID}",
            arguments = listOf(
                navArgument(KEY_ARTICLE_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            ArticleComponent(it)
        }
    }

    @Composable
    private fun ArticleComponent(backStackEntry: NavBackStackEntry) {
        val viewModel = hiltViewModel<ArticleViewModel>()
        val position = backStackEntry.arguments?.getInt(KEY_ARTICLE_ID) ?: DEFAULT_POSITION
        if (!viewModel.isShowContent) {
            viewModel.obtainEvent(ShowContentArticleEvent(position))
        }
        val state: ArticleState? by viewModel.state.collectAsState()
        ObserveState(state)
    }

    @Composable
    private fun ObserveState(state: ArticleState?) {
        state?.let { articleState ->
            when (articleState) {
                is SuccessArticle -> {
                    ArticleUi(articleState.article)
                }
                is EmptyArticle -> {
                    EmptyDetailItemUi()
                }
            }
        }
    }

    @Composable
    private fun ArticleUi(article: ArticleModel) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
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
                    .height(600.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.body1,
                text = article.title,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.body2,
                text = article.summary,
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