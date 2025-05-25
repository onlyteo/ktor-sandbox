package com.onlyteo.sandbox.lib.logging.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.path

fun Application.configureLogging() {
    install(CallLogging) {
        level = org.slf4j.event.Level.DEBUG
        filter { call -> !call.request.path().startsWith("/metrics") }
    }
}