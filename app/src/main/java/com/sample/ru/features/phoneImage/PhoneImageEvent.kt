package com.sample.ru.features.phoneImage

import android.graphics.Bitmap

sealed interface PhoneImageEvent
object LoadSelectedPhoneImageEvent: PhoneImageEvent
class SavePhoneImageEvent(val currentBitmap: Bitmap): PhoneImageEvent
object LoadAllSavedPhoneImageEvent: PhoneImageEvent
object BackFromPhoneImageEvent: PhoneImageEvent