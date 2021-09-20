package com.sample.ru.features.mem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.sample.ru.R
import com.sample.ru.data.model.MemModel
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.KEY_MEM_ID
import com.sample.ru.navigation.Screen
import com.sample.ru.ui.theme.Purple700
import com.sample.ru.util.DEFAULT_POSITION
import com.sample.ru.util.composeContext
import com.sample.ru.util.toDate

class MemScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(
            route = Screen.MemScreen.route + "/{${KEY_MEM_ID}}",
            arguments = listOf(
                navArgument(KEY_MEM_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            MemComponent(it)
        }
    }

    @Composable
    private fun MemComponent(backStackEntry: NavBackStackEntry) {
        val viewModel = hiltViewModel<MemViewModel>()
        val position = backStackEntry.arguments?.getInt(KEY_MEM_ID) ?: DEFAULT_POSITION
        viewModel.obtainEvent(ShowContentMemEvent(position))
        val state: MemState? by viewModel.state.collectAsState()
        ObserveState(state)
    }

    @Composable
    private fun ObserveState(state: MemState?) {
        state?.let { memState ->
            when (memState) {
                is SuccessMem -> {
                    MemUi(memState.mem)
                }
                is EmptyMem -> {
                    EmptyMemUi()
                }
            }
        }
    }

    @Composable
    private fun MemUi(mem: MemModel) {
        Column(modifier = Modifier.fillMaxSize()) {
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

    @Composable
    private fun EmptyMemUi() {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(250.dp)
                    .align(Alignment.Center)
            )
        }
    }

}