package com.onlyteo.sandbox.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.path
import org.slf4j.event.Level

const val LOGGING_PLUGIN_NAME = "LoggingPlugin"

fun Application.configureLogging() {
    log.info("Installing {}", LOGGING_PLUGIN_NAME)
    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> !call.request.path().startsWith("/metrics") }
    }
}