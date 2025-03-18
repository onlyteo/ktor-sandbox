package com.onlyteo.sandbox.errors.handler

import com.onlyteo.sandbox.errors.model.ProblemDetails
import com.onlyteo.sandbox.exception.HttpStatusException
import com.onlyteo.sandbox.exception.ProblemDetailException
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.request.path
import io.ktor.server.response.header
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("com.onlyteo.logger.error")

suspend fun ApplicationCall.handleException(cause: Throwable) {
    val problemDetails = resolveProblemDetails(cause)
    with(problemDetails) {
        response.header(HttpHeaders.ContentType, ContentType.Application.Json.contentType)
        response.call.respond(HttpStatusCode.fromValue(status), problemDetails)
        logger.error(cause.message ?: "Unknown error", cause)
    }
}

private fun ApplicationCall.resolveProblemDetails(cause: Throwable): ProblemDetails {
    return when (cause) {
        is BadRequestException -> ProblemDetails(HttpStatusCode.BadRequest, cause.message, request.path())
        is RequestValidationException -> ProblemDetails(HttpStatusCode.BadRequest, cause.message, request.path())
        is HttpStatusException -> ProblemDetails(cause.status, cause.message, request.path())
        is ProblemDetailException -> cause.problemDetails
        else -> ProblemDetails(HttpStatusCode.InternalServerError, cause.message, request.path())
    }
}