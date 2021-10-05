package com.sample.ru.data.repository

import com.sample.ru.data.service.CacheService
import com.sample.ru.features.sliderPhoto.EmptySliderPhoto
import com.sample.ru.features.sliderPhoto.SliderPhotoState
import com.sample.ru.features.sliderPhoto.SuccessSliderPhoto

class SliderPhotoRepository(private val cacheService: CacheService) {

    private fun photosCache() = cacheService.photosCache()

    fun photos(): SliderPhotoState = if (photosCache().isEmpty()) {
        EmptySliderPhoto
    } else {
        SuccessSliderPhoto(photosCache())
    }

}