package com.sample.ru.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.sample.ru.data.model.ErrorResponse
import com.sample.ru.data.network.InvalidResponseException
import com.sample.ru.data.network.RestExceptionFactory
import okhttp3.ResponseBody
import retrofit2.Response

inline fun <reified T> Response<T>.getBodyDto(): T {
    checkResult()
    return body() ?: throw InvalidResponseException("Response body is null")
}

inline fun <reified E> Response<E>.checkResult() {
    if (!isSuccessful) {
        if (body() == null) {
            val errorBody = errorBody()
            if (errorBody == null) {
                throw RuntimeException("Response body and error body is null")
            } else {
                RestExceptionFactory.throwException(code(), errorBody.getErrorMessage())
            }
        } else {
            RestExceptionFactory.throwException(code(), EMPTY_VALUE)
        }
    }
}

/**
 * Получаем строку сообщения об ошибке из JSON-отображения ошибки
 */
fun ResponseBody.getErrorMessage(): String {
    return try {
        val typeToken = TypeToken.get(ErrorResponse::class.java).type
        val errorResponse: ErrorResponse? = Gson().fromJson<ErrorResponse>(this.string(), typeToken)
        errorResponse?.errorMessage.orEmpty()
    } catch (e: JsonSyntaxException) {
        EMPTY_VALUE
    }
}