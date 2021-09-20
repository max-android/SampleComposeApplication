package com.sample.ru.features.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.ListNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val repository: ListNewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ArticleState?>(null)
    val state: StateFlow<ArticleState?> = _state.asStateFlow()
    var isShowContent = false

    fun obtainEvent(event: ArticleEvent) {
        when (event) {
            is ShowContentArticleEvent -> {
                showContentAction(event.position)
            }
        }
    }

    private fun showContentAction(item: Int) {
        isShowContent = true
        viewModelScope.launch {
            _state.emit(repository.article(item))
        }
    }

}