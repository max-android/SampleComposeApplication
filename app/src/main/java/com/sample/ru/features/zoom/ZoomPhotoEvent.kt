package com.sample.ru.features.zoom

sealed interface ZoomPhotoEvent
@JvmInline
value class ShowContentZoomPhoto(val photoUrl: String?): ZoomPhotoEvent