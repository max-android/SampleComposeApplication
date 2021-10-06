package com.sample.ru.features.phoneImage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.PhoneImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneImageViewModel @Inject constructor(
    private val repository: PhoneImageRepository
) : ViewModel() {

    private val _state = MutableStateFlow<PhoneImageState?>(null)
    val state: StateFlow<PhoneImageState?> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PhoneImageSideEffect?>()
    val sideEffect: SharedFlow<PhoneImageSideEffect?> = _sideEffect.asSharedFlow()

    init {
        obtainEvent(LoadSelectedPhoneImageEvent)
    }

    fun obtainEvent(event: PhoneImageEvent) {
        when (event) {
            is LoadSelectedPhoneImageEvent -> {
                loadSelectedPhoneImageAction()
            }
            is SavePhoneImageEvent -> {
                savePhoneImageAction(event.currentBitmap)
            }
            is LoadAllSavedPhoneImageEvent -> {
                loadAllSavedPhoneImageAction()
            }
            is BackFromPhoneImageEvent -> {
                backFromPhoneImageAction()
            }
        }
    }

    private fun loadSelectedPhoneImageAction() {
        viewModelScope.launch {
            _state.emit(LoadSelectedPhoneImage)
        }
    }

    private fun savePhoneImageAction(currentBitmap: Bitmap) {
        viewModelScope.launch {
            _state.emit(repository.savePhoneImage(currentBitmap))
        }
    }

    private fun loadAllSavedPhoneImageAction() {
        viewModelScope.launch {
            _sideEffect.emit(LoadAllSavedPhoneImage)
        }
    }

    private fun backFromPhoneImageAction() {
        viewModelScope.launch {
            _sideEffect.emit(BackFromPhoneImage)
        }
    }

}