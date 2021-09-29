package com.sample.ru.data.repository

import com.sample.ru.data.local.ProfileSettings

class MainRepository(
    private val profileSettings: ProfileSettings,
) {

    fun isDarkTheme() = profileSettings.themeSwitch

}