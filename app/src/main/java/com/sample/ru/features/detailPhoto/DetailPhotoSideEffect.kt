package com.sample.ru.features.detailPhoto

sealed interface DetailPhotoSideEffect
@JvmInline
value class ShowZoomPhoto(val photoUrl: String): DetailPhotoSideEffect
@JvmInline
value class ShowWebLinkPhoto(val webLink: String): DetailPhotoSideEffect