package com.onlyteo.sandbox.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.request.path
import io.ktor.server.webjars.Webjars
import org.slf4j.event.Level

fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> !call.request.path().startsWith("/metrics") }
    }
}