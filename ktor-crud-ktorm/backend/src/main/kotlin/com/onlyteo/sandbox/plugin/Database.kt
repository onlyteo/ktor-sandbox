package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.database.FlywayPlugin
import com.onlyteo.sandbox.plugin.database.H2Plugin
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureDatabase(context: ApplicationContext) {
    with(context.properties) {

        install(FlywayPlugin) {
            dataSource = context.dataSource
        }
        install(H2Plugin) {
            enabled = h2.console.enabled
            port = h2.console.port
        }
    }
}