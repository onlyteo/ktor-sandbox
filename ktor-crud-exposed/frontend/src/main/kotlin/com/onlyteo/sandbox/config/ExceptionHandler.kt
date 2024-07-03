package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.exception.ProblemDetailException
import io.ktor.http.HttpStatusCode
import java.time.Instant

class ExceptionHandler {

    private val logger = buildLogger

    fun handleException(cause: Throwable, path: String): Pair<HttpStatusCode, Map<String, Any>> {
        val message = cause.message ?: "Unknown error"
        logger.error(message, cause)
        return when (cause) {
            is ProblemDetailException -> {
                val httpStatusCode = HttpStatusCode.fromValue(cause.problemDetails.status)
                httpStatusCode to mapOf(
                    "timestamp" to Instant.now(),
                    "status" to cause.problemDetails.status,
                    "error" to cause.problemDetails.title,
                    "path" to path,
                    "message" to cause.problemDetails.detail,
                    "exception" to cause.javaClass.name
                )
            }

            else -> {
                val httpStatusCode = HttpStatusCode.InternalServerError
                httpStatusCode to mapOf(
                    "timestamp" to Instant.now(),
                    "status" to httpStatusCode.value,
                    "error" to httpStatusCode.description,
                    "path" to path,
                    "message" to message,
                    "exception" to cause.javaClass.name
                )
            }
        }
    }
}