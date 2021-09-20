package com.sample.ru.features.detailPhoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPhotoViewModel @Inject constructor(
    private val repository: GalleryRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DetailPhotoState?>(null)
    val state: StateFlow<DetailPhotoState?> = _state.asStateFlow()
    var isShowContent = false

    fun obtainEvent(event: DetailPhotoEvent) {
        when (event) {
            is ShowContentDetailPhotoEvent -> {
                showContentAction(event.position)
            }
        }
    }

    private fun showContentAction(item: Int) {
        isShowContent = true
        viewModelScope.launch {
            _state.emit(repository.photo(item))
        }
    }

}