package com.sample.ru.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.sample.ru.ui.theme.SampleComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sample.ru.ui.theme.darkThemeColors
import com.sample.ru.ui.theme.lightThemeColors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val state: MainState? by viewModel.state.collectAsState()
            state?.let { mainState ->
                when (mainState) {
                    is SuccessMainState -> {
                        val isDarkMode = remember { mutableStateOf(mainState.isDarkTheme) }
                        SampleComposeAppTheme(
                            darkTheme = isDarkMode.value
                        ) {
                            val systemUiController = rememberSystemUiController()
                            // Set status bar color
                            SideEffect {
                                systemUiController.setSystemBarsColor(
                                    color = if (isDarkMode.value) darkThemeColors.primary
                                    else lightThemeColors.primary,
                                    darkIcons = !isDarkMode.value
                                )
                            }
                            Surface(color = MaterialTheme.colors.background) {
                                MainComponent(
                                    onDarkModeChanged = {
                                        isDarkMode.value = it
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleComposeAppTheme {
        MainComponent {
        }
    }
}