package com.sample.ru.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.ru.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState?>(null)
    val state: StateFlow<ProfileState?> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileSideEffect?>()
    val sideEffect: SharedFlow<ProfileSideEffect?> = _sideEffect.asSharedFlow()

    init {
        obtainEvent(ShowContentProfileEvent)
    }

    fun obtainEvent(event: ProfileEvent) {
        when (event) {
            is ShowContentProfileEvent -> {
                showContentAction()
            }
            is ChangeSwitchEvent -> {
                clickSwitchAction(event.enabledSwitch)
            }
            is EditProfileNameEvent -> {
                editProfileNameAction(event.userName)
            }
            is LoadPhoneImageEvent -> {
                loadPhoneImageAction()
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

    private fun loadPhoneImageAction() {
        viewModelScope.launch {
            _sideEffect.emit(ShowPhoneImage)
        }
    }

}