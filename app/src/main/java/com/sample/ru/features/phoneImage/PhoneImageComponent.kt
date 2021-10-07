package com.sample.ru.features.phoneImage

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.util.composeContext
import com.sample.ru.util.toBitmap
import com.sample.ru.R
import com.sample.ru.features.base.LoadError
import com.sample.ru.navigation.navigateSafe

class PhoneImageScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(route = Screen.PhoneImageScreen.route) {
            PhoneImageComponent(navController)
        }
    }
}

@Composable
private fun PhoneImageComponent(navController: NavController) {
    val viewModel = hiltViewModel<PhoneImageViewModel>()
    val state: PhoneImageState? by viewModel.state.collectAsState()
    val sideEffect: PhoneImageSideEffect? by viewModel.sideEffect.collectAsState(null)
    ObserveState(
        state = state,
        pop = {
            viewModel.obtainEvent(BackFromPhoneImageEvent)
        },
        onSaveImage = { bitmap ->
            viewModel.obtainEvent(SavePhoneImageEvent(bitmap))
        },
        loadImage = {
            viewModel.obtainEvent(LoadAllSavedPhoneImageEvent)
        },
        onAddImage = {
            viewModel.obtainEvent(LoadSelectedPhoneImageEvent)
        }
    )
    ObserveSideEffect(sideEffect, navController)
}

@Composable
private fun ObserveState(
    state: PhoneImageState?, pop: () -> Unit, onSaveImage: (Bitmap) -> Unit,
    loadImage: () -> Unit, onAddImage: () -> Unit
) {
    state?.let { photoImageState ->
        when (photoImageState) {
            is LoadSelectedPhoneImage -> {
                PhoneImageUi(pop, onSaveImage, loadImage, onAddImage)
            }
            is SavePhoneImage -> {
               if (photoImageState.isSuccessfulSave) {
                   PhoneImageUi(pop, onSaveImage, loadImage, onAddImage)
               } else {
                   LoadError()
               }
            }
        }
    }
}

@Composable
private fun ObserveSideEffect(sideEffect: PhoneImageSideEffect?, navController: NavController) {
    sideEffect?.let { phoneImageSideEffect ->
        when (phoneImageSideEffect) {
            is LoadAllSavedPhoneImage -> {
                 navController.navigateSafe(Screen.ListPhoneImageScreen.route)
            }
            is BackFromPhoneImage -> {
                navController.popBackStack(route = Screen.ProfileScreen.route, inclusive = false)
            }
        }
    }
}

@Composable
private fun PhoneImageUi(
    pop: () -> Unit, onSaveImage: (Bitmap) -> Unit, loadImage: () -> Unit, onAddImage: () -> Unit
) {
    val context = composeContext()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val bitmap = uri.toBitmap(context = context)
            bitmap?.let {
                imageBitmap = it
            }
        } else {
            pop.invoke()
        }
    }
    LaunchedEffect(key1 = true) {
        imagePicker.launch("image/*")
    }
    imageBitmap?.let { bitmap ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "selected photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { onSaveImage.invoke(bitmap) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 36.dp, end = 24.dp)
                    .size(68.dp)
            ) {
                Icon(
                    modifier = Modifier.size(68.dp),
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "back icon",
                    tint = MaterialTheme.colors.secondary,
                )
            }

            IconButton(
                onClick = { onAddImage.invoke() },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp)
                    .size(68.dp)
            ) {
                Icon(
                    modifier = Modifier.size(68.dp),
                    painter = painterResource(id = R.drawable.ic_add_circle),
                    contentDescription = "add icon",
                    tint = MaterialTheme.colors.secondary,
                )
            }

            OutlinedButton(
                onClick = {
                    loadImage.invoke()
                },
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(top = 16.dp, bottom = 16.dp, start = 68.dp, end = 68.dp)
                    .height(56.dp)
                    .wrapContentWidth()
                    .align(Alignment.BottomCenter)

            ) {
                Text(
                    text = stringResource(
                        id = R.string.phone_image_load_image
                    ),
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .background(Color.Transparent),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }

}