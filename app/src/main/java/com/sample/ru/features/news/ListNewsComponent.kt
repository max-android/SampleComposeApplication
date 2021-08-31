package com.sample.ru.features.news

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

class ListNewsScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.ListNewsScreen.route) {
            ListNewsComponent(
                navigateTo = { navController.navigateSafe(Screen.DetailNewsScreen.route) }
            )
        }
    }

}

@Composable
fun ListNewsComponent(navigateTo: () -> Unit) {
    Column() {
        Text(text = "ListNews Screen")
        Button(onClick = { navigateTo.invoke() }) {
            Text(text = "GO TO Detail News")
        }
    }
}