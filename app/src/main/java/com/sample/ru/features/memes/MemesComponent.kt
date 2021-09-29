package com.sample.ru.features.memes

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
import com.sample.ru.data.model.BaseModel
import com.sample.ru.data.model.MemModel
import com.sample.ru.features.base.EmptyListUi
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.navigation.navigateSafe
import com.sample.ru.util.composeContext
import com.sample.ru.util.toDate

class MemesScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(
            route = Screen.MemesScreen.route
        ) {
            MemesComponent(navController)
        }
    }

}

@Composable
private fun MemesComponent(navController: NavController) {
    val viewModel = hiltViewModel<MemesViewModel>()
    val state: MemesState? by viewModel.state.collectAsState()
    val sideEffect: MemesSideEffect? by viewModel.sideEffect.collectAsState(null)
    ObserveState(
        state,
        navigateToMem = { position ->
            viewModel.obtainEvent(ClickMemEvent(position))
        })
    ObserveSideEffect(sideEffect, navController)
}

@Composable
private fun ObserveState(state: MemesState?, navigateToMem: (Int) -> Unit) {
    state?.let { memState ->
        when (memState) {
            is SuccessMemes -> {
                MemesUi(memState.memes, navigateToMem)
            }
            is EmptyMemes -> {
                EmptyListUi()
            }
        }
    }
}

@Composable
private fun MemesUi(listMemes: List<BaseModel>, navigateToMem: (Int) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 68.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(listMemes) { index, item ->
            if (item is MemModel) {
                MemItem(item, index, navigateToMem)
            }
        }
    }
}

@Composable
private fun MemItem(mem: MemModel, memPosition: Int, click: (Int) -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable {
                click.invoke(memPosition)
            },
        shape = RoundedCornerShape(16.dp),
    ) {
        Column() {
            Image(
                painter = rememberImagePainter(
                    data = mem.memUrl,
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
                text = mem.title,
            )
            Text(
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onPrimary,
                text = composeContext().getString(
                    R.string.memes_created, mem.created.toDate(), mem.author
                ),
            )
        }
    }
}

@Composable
private fun ObserveSideEffect(sideEffect: MemesSideEffect?, navController: NavController) {
    sideEffect?.let { memSideEffect ->
        when (memSideEffect) {
            is StartMem -> {
                navController.navigateSafe("${Screen.MemScreen.route}/${memSideEffect.position}")
            }
        }
    }
}