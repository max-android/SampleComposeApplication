package com.sample.ru.features.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.ListNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListNewsViewModel @Inject constructor(
    private val repository: ListNewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ListNewsState?>(null)
    val state: StateFlow<ListNewsState?> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ListNewsSideEffect?>()
    val sideEffect: SharedFlow<ListNewsSideEffect?> = _sideEffect.asSharedFlow()

    init {
        obtainEvent(ShowContentListNewsEvent)
    }

    fun obtainEvent(event: ListNewsEvent) {
        when (event) {
            is ShowContentListNewsEvent -> {
                showContentAction()
            }
            is ClickArticleEvent -> {
                clickArticleAction(event.position)
            }
        }
    }

    private fun showContentAction() {
        viewModelScope.launch {
            _state.emit(repository.news())
        }
    }

    private fun clickArticleAction(position: Int) {
        viewModelScope.launch {
            _sideEffect.emit(StartArticle(position))
        }
    }

}