package com.sample.ru.features.gallery

import com.sample.ru.data.model.BaseModel

sealed class GalleryState
class SuccessGallery(
    val photos: List<BaseModel>
) : GalleryState()
object EmptyGallery : GalleryState()