package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.config.handleException
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.handleException(cause)
        }
    }
}