package com.sample.ru.features.home

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

class HomeScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.HomeScreen.route) {
            HomeComponent(
                navigateToNews = { navController.navigateSafe(Screen.ListNewsScreen.route) },
                navigateToMemes = { navController.navigateSafe(Screen.MemesScreen.route) }
            )
        }
    }

}

@Composable
fun HomeComponent(navigateToNews: () -> Unit, navigateToMemes: () -> Unit) {
    Column() {
        Text(text = "Home Screen")
        Button(onClick = { navigateToNews.invoke() }) {
            Text(text = "GO TO List News")
        }
        Button(onClick = { navigateToMemes.invoke() }) {
            Text(text = "GO TO Memes")
        }
    }
}