package com.sample.ru.features.photoWebView

sealed interface PhotoWebViewState
@JvmInline
value class SuccessPhotoWebView(val webLink: String): PhotoWebViewState