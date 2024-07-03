package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.model.ProblemDetails
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.requestvalidation.RequestValidationException

class ExceptionHandler {

    private val logger = buildLogger

    fun handleException(cause: Throwable, path: String): Pair<HttpStatusCode, ProblemDetails> {
        val message = cause.message ?: "Unknown error"
        logger.error(message, cause)
        return when (cause) {
            is RequestValidationException -> {
                val httpStatusCode = HttpStatusCode.BadRequest
                httpStatusCode to ProblemDetails(httpStatusCode, message, path)
            }

            else -> {
                val httpStatusCode = HttpStatusCode.InternalServerError
                httpStatusCode to ProblemDetails(httpStatusCode, message, path)
            }
        }
    }
}