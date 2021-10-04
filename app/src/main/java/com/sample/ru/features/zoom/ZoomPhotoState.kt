package com.sample.ru.features.zoom

sealed interface ZoomPhotoState
@JvmInline
value class SuccessZoomPhoto(val photoUrl: String?): ZoomPhotoState