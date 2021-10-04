package com.sample.ru.util

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.toDate(): String {
   return this.replaceAfter("T", "").replace("T", "")
}

fun String.toEncodedUrl(): String {
   return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
}