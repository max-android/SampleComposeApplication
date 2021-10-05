package com.sample.ru.features.detailPhoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPhotoViewModel @Inject constructor(
    private val repository: GalleryRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DetailPhotoState?>(null)
    val state: StateFlow<DetailPhotoState?> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<DetailPhotoSideEffect?>()
    val sideEffect: SharedFlow<DetailPhotoSideEffect?> = _sideEffect.asSharedFlow()
    var isShowContent = false

    fun obtainEvent(event: DetailPhotoEvent) {
        when (event) {
            is ShowContentDetailPhotoEvent -> {
                showContentAction(event.position)
            }
            is ClickDetailPhotoEvent -> {
                clickDetailPhotoAction(event.photoUrl)
            }
            is ClickWebLinkEvent -> {
                clickWebLinkAction(event.webLink)
            }
        }
    }

    private fun showContentAction(item: Int) {
        isShowContent = true
        viewModelScope.launch {
            _state.emit(repository.photo(item))
        }
    }

    private fun clickDetailPhotoAction(photoUrl: String) {
        viewModelScope.launch {
            _sideEffect.emit(ShowZoomPhoto(photoUrl))
        }
    }

    private fun clickWebLinkAction(webLink: String) {
        viewModelScope.launch {
            _sideEffect.emit(ShowWebLinkPhoto(webLink))
        }
    }

}