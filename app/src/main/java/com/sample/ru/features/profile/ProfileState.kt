package com.sample.ru.features.profile

import com.sample.ru.data.model.ProfileModel

sealed class ProfileState
class SuccessProfile(val profileModel: ProfileModel): ProfileState()