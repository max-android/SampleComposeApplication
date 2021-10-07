package com.sample.ru.util

import android.app.Activity
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun <T> createRestService(
    gsonConverterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient,
    baseUrl: String,
    service: Class<T>
): T {
    return Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(gsonConverterFactory)
        client(okHttpClient)
    }.build().create(service)
}

@Composable
fun composeContext() = LocalContext.current

fun Activity.setTransparentStatusBar(transparent: Boolean = true) {
    if (transparent) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
    } else {
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}