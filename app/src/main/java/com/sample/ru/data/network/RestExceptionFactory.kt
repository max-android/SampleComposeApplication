package com.sample.ru.data.network

import java.net.HttpURLConnection

object RestExceptionFactory {
    /**
     * Фабричный метод генерации исключений при ошибках HTTP
     *
     * @param responseCode Ответ от сервера
     * @throws RuntimeException исключение
     */
    @Throws(Exception::class)
    fun throwException(responseCode: Int, message: String) {
        when (responseCode) {

            HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_FORBIDDEN
            -> throw AccessDeniedException()

            HttpURLConnection.HTTP_NOT_FOUND -> throw ServerProblemException(message)

            HttpURLConnection.HTTP_INTERNAL_ERROR, HttpURLConnection.HTTP_NOT_IMPLEMENTED,
            HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_UNAVAILABLE,
            HttpURLConnection.HTTP_GATEWAY_TIMEOUT, HttpURLConnection.HTTP_VERSION,
            -> throw ServiceUnavailable()

            HttpURLConnection.HTTP_ENTITY_TOO_LARGE -> throw RequestTooLargeException()

            HttpURLConnection.HTTP_BAD_METHOD, HttpURLConnection.HTTP_NOT_ACCEPTABLE
            -> throw UserRequestException()

            UNPROCESSABLE_CODE -> throw UnprocessableException()

            else -> {
                throw Exception(message)
            }
        }
    }

    private const val UNPROCESSABLE_CODE = 422
}