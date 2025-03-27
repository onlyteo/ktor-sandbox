package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.errors.plugin.ErrorHandlingPlugin
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureErrorHandling() {
    install(ErrorHandlingPlugin)
}