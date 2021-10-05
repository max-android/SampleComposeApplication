package com.sample.ru.features.sliderPhoto

import com.sample.ru.data.model.BaseModel

sealed class SliderPhotoState
class SuccessSliderPhoto(
    val photos: List<BaseModel>
): SliderPhotoState()
object EmptySliderPhoto: SliderPhotoState()