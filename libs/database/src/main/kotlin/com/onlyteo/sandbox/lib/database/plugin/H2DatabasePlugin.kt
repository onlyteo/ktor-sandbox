package com.onlyteo.sandbox.lib.database.plugin

import com.onlyteo.sandbox.lib.database.database.H2Database
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl

const val H2_DATABASE_PLUGIN_NAME = "H2DatabasePlugin"

@KtorDsl
class H2DatabasePluginConfig {
    var port: Int? = null
    var enabled: Boolean = true
}

val H2DatabasePlugin: ApplicationPlugin<H2DatabasePluginConfig> =
    createApplicationPlugin(H2_DATABASE_PLUGIN_NAME, ::H2DatabasePluginConfig) {
        application.log.info("Installing {}", H2_DATABASE_PLUGIN_NAME)
        val enabled = pluginConfig.enabled

        if (enabled) {
            val port = checkNotNull(pluginConfig.port) { "Port property must not be null" }
            val database = H2Database(port)

            on(MonitoringEvent(ApplicationStarted)) { application ->
                application.log.info("Starting H2 Console on port $port")
                database.start()
            }

            on(MonitoringEvent(ApplicationStopping)) { application ->
                application.log.info("Stopping H2 Console")
                database.stop()
            }
        }
    }
