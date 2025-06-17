package com.onlyteo.sandbox.app.plugin

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.webjars.Webjars

const val WEBJARS_PLUGIN_NAME = "WebJarsPlugin"

fun Application.configureWebjars() {
    log.info("Installing {}", WEBJARS_PLUGIN_NAME)
    install(Webjars)
}