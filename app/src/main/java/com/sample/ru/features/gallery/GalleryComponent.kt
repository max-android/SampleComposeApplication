package com.sample.ru.features.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.navigation.navigateSafe

class GalleryScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.GalleryScreen.route) {
            GalleryComponent(
                navigateTo = { navController.navigateSafe(Screen.DetailImageScreen.route) }
            )
        }
    }

}

@Composable
fun GalleryComponent(navigateTo: () -> Unit) {
    Column() {
        Text(text = "Gallery Screen")
        Button(onClick = { navigateTo.invoke() }) {
            Text(text = "GO TO Detail IMAGE")
        }
    }
}