package com.sample.ru.data.repository

import com.sample.ru.data.local.ProfileSettings
import com.sample.ru.data.model.ProfileModel
import com.sample.ru.features.profile.ProfileState
import com.sample.ru.features.profile.SuccessProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ProfileRepository(private val profileSettings: ProfileSettings) {

    fun loadProfile(): Flow<ProfileState> {
        return combine(profileSettings.themeSwitch, profileSettings.userName)
        { themeSwitch: Boolean, userName: String ->
            SuccessProfile(ProfileModel(userName, themeSwitch))
        }
    }

    suspend fun setUserName(userName: String) {
        profileSettings.setUserName(userName)
    }

    suspend fun setEnabledSwitch(enabledSwitch: Boolean) {
        profileSettings.setEnabledSwitch(enabledSwitch)
    }

}