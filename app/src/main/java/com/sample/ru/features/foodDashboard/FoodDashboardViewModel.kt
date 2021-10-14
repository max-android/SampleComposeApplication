package com.sample.ru.features.foodDashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.FoodDashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDashboardViewModel @Inject constructor(
    val repository: FoodDashboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FoodDashboardState>(LoadingFoodDashboard)
    val state: StateFlow<FoodDashboardState> = _state.asStateFlow()

    init {
        obtainEvent(ShowContentFoodDashboardEvent)
    }

    fun obtainEvent(event: FoodDashboardEvent) {
        when (event) {
            is ShowContentFoodDashboardEvent -> {
                showContentAction()
            }
        }
    }

    private fun showContentAction() {
        viewModelScope.launch {
            _state.emit(repository.loadFoodDashboard())
        }
    }

}