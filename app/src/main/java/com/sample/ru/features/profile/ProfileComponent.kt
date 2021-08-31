package com.sample.ru.features.profile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sample.ru.navigation.ComposeNavFactory
import com.sample.ru.navigation.Screen
import com.sample.ru.navigation.navigateSafe

class ProfileScreenFactory : ComposeNavFactory {

    override fun create(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(Screen.ProfileScreen.route) {
            ProfileComponent()
        }
    }

}

@Composable
fun ProfileComponent() {
    Text(text = "Profile Screen")
}