package com.sample.ru.features.memes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.ui.theme.Purple700
import com.sample.ru.util.composeContext
import com.sample.ru.util.toDate

class MemesScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.MemesScreen.route) {
            MemesComponent()
        }
    }

}

@Composable
private fun MemesComponent() {
    val viewModel = hiltViewModel<MemesViewModel>()
    val state: MemesState? by viewModel.state.collectAsState()
    ObserveState(state)
}

@Composable
private fun ObserveState(state: MemesState?) {
    state?.let { memState ->
        when (memState) {
            is SuccessMemes -> {
                MemesUi(memState.memes)
            }
            is EmptyMemes -> {
                EmptyMemesUi()
            }
        }
    }
}

@Composable
private fun MemesUi(listMemes: List<BaseModel>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listMemes) { item ->
            if (item is MemModel) {
                MemItem(item)
            }
        }
    }
}

@Composable
private fun EmptyMemesUi() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_view_list),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .size(250.dp)
                .align(Alignment.Center)
        )
    }
}


@Composable
private fun MemItem(mem: MemModel) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
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
                color = Color.Black,
                text = mem.title,
            )
            Text(
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.caption,
                color = Purple700,
                text = composeContext().getString(
                    R.string.memes_created, mem.created.toDate(), mem.author
                ),
            )
        }
    }
}