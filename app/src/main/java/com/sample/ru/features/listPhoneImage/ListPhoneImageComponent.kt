package com.sample.ru.features.listPhoneImage

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.ru.features.base.EmptyListUi
import com.sample.ru.features.base.LoadError
import com.sample.ru.features.base.LoadProgress
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import java.io.File

class ListPhoneImageScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(route = Screen.ListPhoneImageScreen.route) {
            ListPhoneImageComponent()
        }
    }
}

@Composable
private fun ListPhoneImageComponent() {
    val viewModel = hiltViewModel<ListPhoneImageViewModel>()
    val state: ListPhoneImageState? by viewModel.state.collectAsState()
    ObserveState(
        state = state,
        onDeleteImage = { fileName ->
            viewModel.obtainEvent(ClickDeletePhoneImageEvent(fileName))
        }
    )
}

@Composable
private fun ObserveState(
    state: ListPhoneImageState?, onDeleteImage: (String) -> Unit
) {
    state?.let { listPhoneImageState ->
        when (listPhoneImageState) {
            is LoadingListPhoneImage -> {
                LoadProgress()
            }
            is SuccessListPhoneImage -> {
                ListPhoneImageUi(listPhoneImageState.files, onDeleteImage)
            }
            is ErrorListPhoneImage -> {
                LoadError()
            }
            is EmptyListPhoneImage -> {
                EmptyListUi()
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ListPhoneImageUi(files: List<Pair<File, Bitmap>>, onDeleteImage: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        itemsIndexed(items = files) { _, image ->
            val fileName = image.first.name
            val bitmap = image.second
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) onDeleteImage.invoke(fileName)
                    true
                }
            )
            ImageSwipeToDeleteItem(bitmap = bitmap, title = fileName, date = "empty", state = state)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ImageSwipeToDeleteItem(
    bitmap: Bitmap,
    title: String,
    date: String,
    state: DismissState
) {
    SwipeToDismiss(
        state = state,
        background = {
            val color = when (state.dismissDirection) {
                DismissDirection.StartToEnd -> Color.Transparent
                DismissDirection.EndToStart -> Color.Red
                null -> MaterialTheme.colors.surface
            }
            DeleteDismissItem(color)
        },
        dismissContent = {
            SavedImageItem(bitmap = bitmap, title = title, date = date)
        },
        directions = setOf(DismissDirection.EndToStart)
    )
}

@Composable
private fun DeleteDismissItem(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun SavedImageItem(bitmap: Bitmap, title: String, date: String) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .height(80.dp)
                        .width(60.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = date,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}