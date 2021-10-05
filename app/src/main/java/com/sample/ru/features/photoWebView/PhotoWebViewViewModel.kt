package com.sample.ru.features.photoWebView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoWebViewViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<PhotoWebViewState?>(null)
    val state: StateFlow<PhotoWebViewState?> = _state.asStateFlow()

    fun obtainEvent(event: PhotoWebViewEvent) {
        when (event) {
            is ShowContentPhotoWebView -> {
                showContentPhotoWebViewAction(event.webLink)
            }
        }
    }

    private fun showContentPhotoWebViewAction(webLink: String) {
        viewModelScope.launch {
            _state.emit(SuccessPhotoWebView(webLink))
        }
    }

}