package com.sample.ru.features.detailPhoto

import com.sample.ru.data.model.PhotoModel

sealed class DetailPhotoState
class SuccessDetailPhoto(
    val photo: PhotoModel
): DetailPhotoState()
object EmptyDetailPhoto: DetailPhotoState()