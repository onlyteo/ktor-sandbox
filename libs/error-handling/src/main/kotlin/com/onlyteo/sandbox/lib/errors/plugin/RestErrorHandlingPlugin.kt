package com.onlyteo.sandbox.lib.errors.plugin

import com.onlyteo.sandbox.lib.errors.handler.handleException
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.statuspages.StatusPages

const val REST_ERROR_HANDLING_PLUGIN_NAME: String = "RestErrorHandlingPlugin"

class RestErrorHandlingPluginConfig {
}

val RestErrorHandlingPlugin: ApplicationPlugin<RestErrorHandlingPluginConfig> =
    createApplicationPlugin(REST_ERROR_HANDLING_PLUGIN_NAME, ::RestErrorHandlingPluginConfig) {
        application.log.info("Installing {}", REST_ERROR_HANDLING_PLUGIN_NAME)

        application.install(StatusPages) {
            exception<Throwable> { call: ApplicationCall, cause: Throwable ->
                call.handleException(cause)
            }
        }
    }