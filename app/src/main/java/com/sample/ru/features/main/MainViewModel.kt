package com.sample.ru.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _state = MutableStateFlow<MainState?>(null)
    val state: StateFlow<MainState?> = _state.asStateFlow()

    init {
         viewModelScope.launch {
             repository.isDarkTheme().collect {
                 _state.emit(SuccessMainState(it))
             }
         }
    }

}