package com.sample.ru.features.photoWebView

sealed interface PhotoWebViewEvent
@JvmInline
value class ShowContentPhotoWebView(val webLink: String): PhotoWebViewEvent