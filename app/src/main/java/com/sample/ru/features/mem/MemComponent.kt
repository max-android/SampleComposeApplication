package com.sample.ru.features.mem

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
import com.sample.ru.data.model.MemModel
import com.sample.ru.features.base.EmptyDetailItemUi
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.KEY_MEM_ID
import com.sample.ru.navigation.Screen
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
        if (!viewModel.isShowContent) {
            viewModel.obtainEvent(ShowContentMemEvent(position))
        }
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
                    EmptyDetailItemUi()
                }
            }
        }
    }

    @Composable
    private fun MemUi(mem: MemModel) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
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
                    .height(600.dp)
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