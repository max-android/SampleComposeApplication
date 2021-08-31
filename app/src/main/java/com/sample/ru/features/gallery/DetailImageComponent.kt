package com.sample.ru.features.gallery

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen

class DetailImageScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.DetailImageScreen.route) {
            DetailImageComponent()
        }
    }

}

@Composable
fun DetailImageComponent() {
    Text(text = "Detail Image")
}