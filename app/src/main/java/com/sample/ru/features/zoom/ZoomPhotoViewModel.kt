package com.sample.ru.features.zoom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZoomPhotoViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<ZoomPhotoState?>(null)
    val state: StateFlow<ZoomPhotoState?> = _state.asStateFlow()

    fun obtainEvent(event: ZoomPhotoEvent) {
        when (event) {
            is ShowContentZoomPhoto -> {
                showContentZoomPhotoAction(event.photoUrl)
            }
        }
    }

    private fun showContentZoomPhotoAction(photoUrl: String?) {
        viewModelScope.launch {
            _state.emit(SuccessZoomPhoto(photoUrl))
        }
    }

}