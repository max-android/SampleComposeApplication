package com.sample.ru.features.memes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.MemesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemesViewModel @Inject constructor(
    private val repository: MemesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MemesState?>(null)
    val state: StateFlow<MemesState?> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MemesSideEffect?>()
    val sideEffect: SharedFlow<MemesSideEffect?> = _sideEffect.asSharedFlow()

    init {
        obtainEvent(ShowContentMemesEvent)
    }

    fun obtainEvent(event: MemesEvent) {
        when (event) {
            is ShowContentMemesEvent -> {
                showContentAction()
            }
            is ClickMemEvent -> {
                clickMemEvent(event.position)
            }
        }
    }

    private fun showContentAction() {
        viewModelScope.launch {
            _state.emit(repository.memes())
        }
    }

    private fun clickMemEvent(position: Int) {
        viewModelScope.launch {
            _sideEffect.emit(StartMem(position))
        }
    }

}