package com.sample.ru.features.foodDashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.sample.ru.R
import com.sample.ru.features.base.EmptyListUi
import com.sample.ru.features.base.LoadError
import com.sample.ru.features.base.LoadProgress
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen

class FoodDashboardScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.FoodDashboardScreen.route) {
            FoodDashboardComponent()
        }
    }

}

@Composable
private fun FoodDashboardComponent() {
    val viewModel = hiltViewModel<FoodDashboardViewModel>()
    val state: FoodDashboardState? by viewModel.state.collectAsState()
    ObserveState(state)
}

@Composable
private fun ObserveState(state: FoodDashboardState?) {
    state?.let { fdState ->
        when (fdState) {
            is LoadingFoodDashboard -> {
                LoadProgress()
            }
            is SuccessFoodDashboard -> {
                FoodDashboardUi(fdState.foods)
            }
            is EmptyFoodDashboard -> {
                EmptyListUi()
            }
            is ErrorFoodDashboard -> {
                LoadError()
            }
        }
    }
}

@Composable
private fun FoodDashboardUi(foods: List<BaseFoodDashboard>) {
    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 68.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(foods) { _, item ->
            when (item) {
                is HeaderFoodDashboard -> {
                    HeaderFoodDashboardItem(item)
                }
                is DetailFoodDashboard -> {
                    DetailFoodDashboardItem(item)
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun DetailFoodDashboardItem(item: DetailFoodDashboard) {
    Box {
        Image(
            painter = rememberImagePainter(
                data = item.image,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder)
                    transformations(RoundedCornersTransformation(16f))
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onPrimary,
                text = item.title,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary,
                text = item.subTitle,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_star_rate),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                )
                Text(
                    text = item.rating,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_reviews),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                )
                Text(
                    text = item.rating,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.food_dashboard_is_free),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary,
                )
                Image(
                    painter = painterResource(R.drawable.ic_baseline_check),
                    contentDescription = null,
                    colorFilter = if (item.hasFreeDelivery)
                        ColorFilter.tint(MaterialTheme.colors.primaryVariant) else
                        ColorFilter.tint(MaterialTheme.colors.onPrimary)
                )
            }
        }
    }

}

@Composable
private fun HeaderFoodDashboardItem(item: HeaderFoodDashboard) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.primaryVariant,
        text = item.header,
    )
}
