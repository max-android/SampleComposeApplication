package com.sample.ru.features.listPhoneImage

import android.graphics.Bitmap
import java.io.File

sealed interface ListPhoneImageState
object LoadingListPhoneImage : ListPhoneImageState
class SuccessListPhoneImage(
    val files: List<Pair<File, Bitmap>>
) : ListPhoneImageState
object ErrorListPhoneImage: ListPhoneImageState
object EmptyListPhoneImage: ListPhoneImageState