package com.sample.ru.data.network

import java.io.IOException

class NoInternetException(cause: Throwable) : IOException(cause)

class AccessDeniedException : Exception()

class NotAuthorizedException : Exception()

class ServiceUnavailable : Exception()

open class ServerProblemException(causeString: String) : Exception(causeString)

class PagesOverflowException : Exception()

class FieldException(val field: String, val fieldMessage: String) : Exception()

class ServerValidationFieldException(val fieldMessage: String) : Exception()

class ValidationException(message: String) : Exception(message)

class RequestTooLargeException : Exception()

class ParseTimeException(message: String = "Parse time error") : Exception(message)

/**
 * Неправильный ответ сервера (например, не хватает чего-либо в ответе)
 */
class InvalidResponseException(message: String = "") : Exception(message)

/**
 * Неправильный запрос на сервер
 */
class UserRequestException(message: String = "") : Exception(message)

class UserBlockedException(message: String = "") : Exception(message)

class InvalidImageException(causeString: String, val fileName: String) : Exception(causeString)

class NullException(message: String = "not found id") : Exception(message)

class SharedException(message: String = "Can't share compatibility zodiac sign exception") : Exception(message)

/**
 * Прочее
 */
class UnprocessableException(message: String = "The given data was invalid.") : Exception(message)