package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.config.ExceptionHandler
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.thymeleaf.ThymeleafContent

fun Application.configureErrorHandling(exceptionHandler: ExceptionHandler) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val (httpStatusCode, model) = exceptionHandler.handleException(cause, call.request.path())
            call.respond(
                httpStatusCode, ThymeleafContent(
                    template = "error",
                    model = model
                )
            )
        }
    }
}