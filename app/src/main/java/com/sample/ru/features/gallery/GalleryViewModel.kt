package com.sample.ru.features.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: GalleryRepository
) : ViewModel() {

    private val _state = MutableStateFlow<GalleryState?>(null)
    val state: StateFlow<GalleryState?> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<GallerySideEffect?>()
    val sideEffect: SharedFlow<GallerySideEffect?> = _sideEffect.asSharedFlow()

    init {
        obtainEvent(ShowContentGalleryEvent)
    }

    fun obtainEvent(event: GalleryEvent) {
        when (event) {
            is ShowContentGalleryEvent -> {
                showContentAction()
            }
            is ClickItemGalleryEvent -> {
                clickPhotoAction(event.position)
            }
            is ClickSliderPhotoEvent -> {
                clickSliderPhotoAction()
            }
        }
    }

    private fun showContentAction() {
        viewModelScope.launch {
            _state.emit(repository.photos())
        }
    }

    private fun clickPhotoAction(position: Int) {
        viewModelScope.launch {
            _sideEffect.emit(ShowPhoto(position))
        }
    }

    private fun clickSliderPhotoAction() {
        viewModelScope.launch {
            _sideEffect.emit(ShowSliderPhoto)
        }
    }

}