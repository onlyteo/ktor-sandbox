package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.model.ProblemDetails
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import io.ktor.server.response.respond

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val httpStatusCode = HttpStatusCode.InternalServerError
            val detail = cause.message ?: "Unknown error"
            val problemDetails = ProblemDetails(httpStatusCode, detail, call.request.path())
            call.respond(httpStatusCode, problemDetails)
        }
    }
}