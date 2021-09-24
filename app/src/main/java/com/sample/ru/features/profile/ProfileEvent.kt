package com.sample.ru.features.profile

sealed interface ProfileEvent
object ShowContentProfileEvent: ProfileEvent
@JvmInline
value class ChangeSwitch(val enabledSwitch: Boolean): ProfileEvent
@JvmInline
value class EditProfileName(val userName: String): ProfileEvent