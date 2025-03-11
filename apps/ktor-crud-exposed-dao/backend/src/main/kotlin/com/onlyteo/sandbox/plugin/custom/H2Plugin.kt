package com.onlyteo.sandbox.plugin.custom

import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import org.h2.tools.Server


@KtorDsl
class H2PluginConfig {
    var enabled: Boolean? = false
    var port: Int? = null
}

val H2Plugin: ApplicationPlugin<H2PluginConfig> =
    createApplicationPlugin("H2Plugin", ::H2PluginConfig) {
        if (pluginConfig.enabled == true) {
            val port = checkNotNull(pluginConfig.port) { "Port property must not be null" }
            application.log.info("H2Plugin initialized")

            val h2Server = Server.createWebServer("-webPort", "$port", "-tcpAllowOthers")

            on(MonitoringEvent(DataSourceReady)) { application ->
                application.log.info("Starting H2 Console on port $port")
                h2Server.start()
            }

            on(MonitoringEvent(DataSourceClosing)) { application ->
                application.log.info("Stopping H2 Console")
                h2Server.stop()
            }
        } else {
            application.log.info("H2Plugin disabled")
        }
    }
