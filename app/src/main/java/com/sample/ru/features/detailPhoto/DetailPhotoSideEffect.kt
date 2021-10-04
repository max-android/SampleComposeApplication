package com.sample.ru.features.detailPhoto

sealed interface DetailPhotoSideEffect
@JvmInline
value class ShowZoomPhoto(val photoUrl: String): DetailPhotoSideEffect