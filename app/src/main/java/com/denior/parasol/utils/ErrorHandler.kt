package com.denior.parasol.utils

import retrofit2.HttpException
import java.io.IOException

object ErrorHandler {
    data class ErrorData(val message: String, val isRecoverable: Boolean)

    fun handleError(
        exception: Exception
    ): ErrorData {
        return when (exception) {
            is IOException -> ErrorData(
                message = "Network error: ${exception.message}",  // Сообщение об ошибке
                isRecoverable = true
            )

            is HttpException -> ErrorData(
                message = "HTTP error: ${exception.code()} - ${exception.message()}",  // Сообщение с кодом и описанием
                isRecoverable = false
            )

            else -> ErrorData(
                message = "Unexpected error: ${exception.message}",  // Для других ошибок
                isRecoverable = false
            )
        }
    }
}


