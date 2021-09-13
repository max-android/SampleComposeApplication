package com.sample.ru.features.memes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.MemesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemesViewModel @Inject constructor(
    private val repository: MemesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MemesState?>(null)
    val state: StateFlow<MemesState?> = _state.asStateFlow()

    init {
        obtainEvent(ShowContentMemesEvent)
    }

    private fun obtainEvent(event: MemesEvent) {
        when (event) {
            is ShowContentMemesEvent -> {
                showContentAction()
            }
        }
    }

    private fun showContentAction() {
        viewModelScope.launch {
            _state.emit(repository.memes())
        }
    }

}