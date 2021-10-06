package com.sample.ru.features.listPhoneImage

sealed interface ListPhoneImageEvent
object ShowContentListPhoneImageEvent: ListPhoneImageEvent
@JvmInline
value class ClickDeletePhoneImageEvent(val fileName: String): ListPhoneImageEvent