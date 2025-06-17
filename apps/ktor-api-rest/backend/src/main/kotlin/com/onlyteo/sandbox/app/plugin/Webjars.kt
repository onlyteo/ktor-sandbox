package com.onlyteo.sandbox.app.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.webjars.Webjars

fun Application.configureWebjars() {
    install(Webjars)
}