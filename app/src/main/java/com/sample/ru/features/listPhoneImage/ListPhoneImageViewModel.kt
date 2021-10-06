package com.sample.ru.features.listPhoneImage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.PhoneImageRepository
import com.sample.ru.util.BASE_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPhoneImageViewModel @Inject constructor(
    private val repository: PhoneImageRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ListPhoneImageState?>(null)
    val state: StateFlow<ListPhoneImageState?> = _state.asStateFlow()

    init {
        obtainEvent(ShowContentListPhoneImageEvent)
    }

    fun obtainEvent(event: ListPhoneImageEvent) {
        when (event) {
            is ShowContentListPhoneImageEvent -> {
                showContentListPhoneImageAction()
            }
            is ClickDeletePhoneImageEvent -> {
                clickDeletePhoneImageAction(event.fileName)
            }
        }
    }

    private fun showContentListPhoneImageAction() {
        viewModelScope.launch {
            _state.emit(LoadingListPhoneImage)
            delay(BASE_DELAY)
            _state.emit(repository.getSavedImages())
        }
    }

    private fun clickDeletePhoneImageAction(fileName: String) {
        viewModelScope.launch {
            _state.emit(LoadingListPhoneImage)
            delay(BASE_DELAY)
            _state.emit(repository.deleteImage(fileName))
        }
    }

}