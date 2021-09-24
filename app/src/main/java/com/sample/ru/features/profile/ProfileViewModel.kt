package com.sample.ru.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState?>(null)
    val state: StateFlow<ProfileState?> = _state.asStateFlow()

    init {
        obtainEvent(ShowContentProfileEvent)
    }

    fun obtainEvent(event: ProfileEvent) {
        when (event) {
            is ShowContentProfileEvent -> {
                showContentAction()
            }
            is ChangeSwitch -> {
                clickSwitchAction(event.enabledSwitch)
            }
            is EditProfileName -> {
                editProfileNameAction(event.userName)
            }
        }
    }

    private fun showContentAction() {
        viewModelScope.launch {
            repository.loadProfile().collect { profileState ->
                _state.emit(profileState)
            }
        }
    }

    private fun clickSwitchAction(enabledSwitch: Boolean) {
        viewModelScope.launch {
            repository.setEnabledSwitch(enabledSwitch)
        }
    }

    private fun editProfileNameAction(userName: String) {
        viewModelScope.launch {
            repository.setUserName(userName)
        }
    }


}