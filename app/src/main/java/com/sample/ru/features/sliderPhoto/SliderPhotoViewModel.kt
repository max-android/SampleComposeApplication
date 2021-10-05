package com.sample.ru.features.sliderPhoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.SliderPhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SliderPhotoViewModel @Inject constructor(
    private val repository: SliderPhotoRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SliderPhotoState?>(null)
    val state: StateFlow<SliderPhotoState?> = _state.asStateFlow()

    init {
        obtainEvent(ShowContentSliderPhoto)
    }

    fun obtainEvent(event: SliderPhotoEvent) {
        when (event) {
            is ShowContentSliderPhoto -> {
                showContentSliderPhotoAction()
            }
        }
    }

    private fun showContentSliderPhotoAction() {
        viewModelScope.launch {
            _state.emit(repository.photos())
        }
    }

}