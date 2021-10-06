package com.sample.ru.features.phoneImage

sealed interface PhoneImageState
object LoadSelectedPhoneImage: PhoneImageState
class SavePhoneImage(val isSuccessfulSave: Boolean): PhoneImageState