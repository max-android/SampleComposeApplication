package com.sample.ru.features.mem

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
class MemViewModel @Inject constructor(
    private val repository: MemesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MemState?>(null)
    val state: StateFlow<MemState?> = _state.asStateFlow()

    fun obtainEvent(event: MemEvent) {
        when (event) {
            is ShowContentMemEvent -> {
                showContentAction(event.position)
            }
        }
    }

    private fun showContentAction(item: Int) {
        viewModelScope.launch {
            _state.emit(repository.mem(item))
        }
    }

}