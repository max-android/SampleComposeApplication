package com.sample.ru.data.repository

import com.sample.ru.data.local.ProfileSettings
import com.sample.ru.data.model.ProfileModel
import com.sample.ru.features.profile.ProfileState
import com.sample.ru.features.profile.SuccessProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class ProfileRepository(private val profileSettings: ProfileSettings) {

    suspend fun loadProfile(): Flow<ProfileState> {
       return profileSettings.enabledSwitch.zip(profileSettings.userName)
        { enabledSwitch: Boolean, userName: String ->
            SuccessProfile(ProfileModel(userName, enabledSwitch))
        }
    }

    suspend fun setUserName(userName: String) {
        profileSettings.setUserName(userName)
    }

    suspend fun setEnabledSwitch(enabledSwitch: Boolean) {
        profileSettings.setEnabledSwitch(enabledSwitch)
    }

}