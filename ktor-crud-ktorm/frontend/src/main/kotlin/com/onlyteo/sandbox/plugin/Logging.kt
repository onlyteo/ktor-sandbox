package com.onlyteo.sandbox.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.path
import org.slf4j.event.Level

fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> !call.request.path().startsWith("/metrics") }
    }
}