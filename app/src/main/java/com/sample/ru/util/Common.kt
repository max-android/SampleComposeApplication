package com.sample.ru.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.toDate(): String {
   return this.replaceAfter("T", "").replace("T", "")
}

fun String.toEncodedUrl(): String {
   return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
}

fun Uri.toBitmap(context: Context): Bitmap? {
   val bitmap = mutableStateOf<Bitmap?>(null)

   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      val source = ImageDecoder.createSource(context.contentResolver, this)
      bitmap.value = ImageDecoder.decodeBitmap(source)
   } else {
      bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, this)
   }
   return bitmap.value
}

fun String.toJpeg(): String{
   return this.plus(".jpg")
}