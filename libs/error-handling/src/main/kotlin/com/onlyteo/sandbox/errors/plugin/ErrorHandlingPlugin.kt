package com.onlyteo.sandbox.errors.plugin

import com.onlyteo.sandbox.errors.handler.handleException
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.statuspages.StatusPages

const val ERROR_HANDLING_PLUGIN_NAME: String = "ErrorHandlingPlugin"

class ErrorHandlingPluginConfig {
}

val ErrorHandlingPlugin: ApplicationPlugin<ErrorHandlingPluginConfig> =
    createApplicationPlugin("ErrorHandlingPlugin", ::ErrorHandlingPluginConfig) {
        application.log.info("Installerer {}", ERROR_HANDLING_PLUGIN_NAME)

        application.install(StatusPages) {
            exception<Throwable> { call: ApplicationCall, cause: Throwable ->
                call.handleException(cause)
            }
        }
    }