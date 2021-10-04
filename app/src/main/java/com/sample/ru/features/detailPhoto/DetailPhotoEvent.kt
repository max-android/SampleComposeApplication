package com.sample.ru.features.detailPhoto

sealed interface DetailPhotoEvent
@JvmInline
value class ShowContentDetailPhotoEvent(val position: Int): DetailPhotoEvent
@JvmInline
value class ClickDetailPhotoEvent(val photoUrl: String): DetailPhotoEvent