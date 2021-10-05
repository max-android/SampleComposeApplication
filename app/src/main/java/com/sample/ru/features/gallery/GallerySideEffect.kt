package com.sample.ru.features.gallery

sealed interface GallerySideEffect
@JvmInline
value class ShowPhoto(val position: Int) : GallerySideEffect
object ShowSliderPhoto: GallerySideEffect