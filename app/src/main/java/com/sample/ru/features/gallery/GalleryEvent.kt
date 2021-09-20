package com.sample.ru.features.gallery

sealed interface GalleryEvent
object ShowContentGalleryEvent: GalleryEvent
@JvmInline
value class ClickItemGalleryEvent(val position: Int): GalleryEvent