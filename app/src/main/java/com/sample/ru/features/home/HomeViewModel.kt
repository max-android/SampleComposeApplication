package com.sample.ru.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState?>(LoadingHome)
    val state: StateFlow<HomeState?> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect?>()
    val sideEffect: SharedFlow<HomeSideEffect?> = _sideEffect.asSharedFlow()

    init {
        obtainEvent(ShowContentHomeEvent)
    }

    fun obtainEvent(event: HomeEvent) {
        when (event) {
            is ShowContentHomeEvent -> {
                showContentAction()
            }
            is ClickArticleHomeEvent -> {
                clickArticleAction()
            }
            is ClickMemHomeEvent -> {
                clickMemAction()
            }
            is ClickFoodDashboardEvent -> {
                clickFoodDashboardAction()
            }
        }
    }

    private fun showContentAction() {
        viewModelScope.launch {
            _state.emit(repository.loadHomeData())
        }
    }

    private fun clickArticleAction() {
        viewModelScope.launch {
            _sideEffect.emit(StartNews)
        }
    }

    private fun clickMemAction() {
        viewModelScope.launch {
            _sideEffect.emit(StartMemes)
        }
    }

    private fun clickFoodDashboardAction() {
        viewModelScope.launch {
            _sideEffect.emit(StartFoodDashboard)
        }
    }

}