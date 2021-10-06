package com.sample.ru.features.profile

sealed interface ProfileEvent
object ShowContentProfileEvent: ProfileEvent
@JvmInline
value class ChangeSwitchEvent(val enabledSwitch: Boolean): ProfileEvent
@JvmInline
value class EditProfileNameEvent(val userName: String): ProfileEvent
object LoadPhoneImageEvent: ProfileEvent