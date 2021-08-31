package com.sample.ru.features.news

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen

class DetailNewsScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.DetailNewsScreen.route) {
            DetailNewsComponent()
        }
    }

}

@Composable
fun DetailNewsComponent() {
    Text(text = "DetailNews Screen")
}