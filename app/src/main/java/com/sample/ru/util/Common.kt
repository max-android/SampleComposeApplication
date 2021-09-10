package com.sample.ru.util

fun String.toDate(): String {
   return this.replaceAfter("T", "").replace("T", "")
}