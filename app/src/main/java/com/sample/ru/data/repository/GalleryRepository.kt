package com.sample.ru.data.repository

import com.sample.ru.data.model.PhotoModel
import com.sample.ru.data.service.CacheService
import com.sample.ru.features.detailPhoto.DetailPhotoState
import com.sample.ru.features.detailPhoto.EmptyDetailPhoto
import com.sample.ru.features.detailPhoto.SuccessDetailPhoto
import com.sample.ru.features.gallery.EmptyGallery
import com.sample.ru.features.gallery.GalleryState
import com.sample.ru.features.gallery.SuccessGallery

class GalleryRepository(private val cacheService: CacheService) {

    private fun photosCache() = cacheService.photosCache()

    fun photos(): GalleryState = if (photosCache().isEmpty()) {
        EmptyGallery
    } else {
        SuccessGallery(photosCache())
    }

    fun photo(item: Int): DetailPhotoState {
        val photoModel = photosCache().getOrNull(item)
        return if (photoModel != null && photoModel is PhotoModel) {
            SuccessDetailPhoto(photoModel)
        } else {
            EmptyDetailPhoto
        }
    }

}