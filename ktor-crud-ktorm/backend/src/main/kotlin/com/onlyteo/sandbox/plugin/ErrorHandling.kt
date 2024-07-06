package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.config.ExceptionHandler
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import io.ktor.server.response.respond

fun Application.configureErrorHandling() {
    val exceptionHandler = ExceptionHandler()

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val (httpStatusCode, problemDetails) = exceptionHandler.handleException(cause, call.request.path())
            call.respond(httpStatusCode, problemDetails)
        }
    }
}