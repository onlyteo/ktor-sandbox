package com.onlyteo.sandbox.plugin.custom

import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
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
        val enabled = checkNotNull(pluginConfig.enabled) { "Enabled property must not be null" }

        if (enabled) {
            val port = checkNotNull(pluginConfig.port) { "Port property must not be null" }

            val h2Server = H2Server(port)

            on(MonitoringEvent(ApplicationStarted)) { application ->
                application.log.info("Starting H2 Console on port $port")
                h2Server.start()
            }

            on(MonitoringEvent(ApplicationStopping)) { application ->
                application.log.info("Stopping H2 Console")
                h2Server.stop()
            }
        }
    }

private class H2Server(private val webServer: Server) {
    constructor(port: Int) : this(Server.createWebServer("-webPort", "$port", "-tcpAllowOthers"))

    fun start() {
        webServer.start()
    }

    fun stop() {
        webServer.stop()
    }
}