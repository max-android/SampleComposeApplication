package com.sample.ru.features.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HomeState())

    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    fun updateCounter() {
        _state.value = state.value.copy(counter = state.value.counter.plus(1))
    }

    fun updateSlider(value: Int) {
        _state.value = state.value.copy(sliderValue = value)
    }

}