package com.sample.ru.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun <T> createRestService(
    gsonConverterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient,
    baseUrl: String,
    service: Class<T>
): T {
    return Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(gsonConverterFactory)
        client(okHttpClient)
    }.build().create(service)
}