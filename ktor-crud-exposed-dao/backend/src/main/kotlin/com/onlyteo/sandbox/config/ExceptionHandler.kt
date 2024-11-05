package com.onlyteo.sandbox.config

import com.onlyteo.sandbox.exception.HttpStatusException
import com.onlyteo.sandbox.exception.ProblemDetailException
import com.onlyteo.sandbox.model.ProblemDetails
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.request.path
import io.ktor.server.response.respond

private val logger = buildErrorLogger

suspend fun ApplicationCall.handleException(cause: Throwable) {
    val problemDetails = resolveProblemDetails(cause)
    //response.header(HttpHeaders.ContentType, ContentType.Application.Json.contentType)
    response.call.respond(HttpStatusCode.fromValue(problemDetails.status), problemDetails)
    logger.error(cause.message ?: "Unknown error", cause)
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