package com.onlyteo.sandbox.app.plugin

import com.onlyteo.sandbox.app.context.ApplicationContext
import com.onlyteo.sandbox.lib.database.plugin.FlywayPlugin
import com.onlyteo.sandbox.lib.database.plugin.H2DatabasePlugin
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureDatabase(applicationContext: ApplicationContext) {
    with(applicationContext) {
        install(FlywayPlugin) {
            dataSource = hikariDataSource
        }
        install(H2DatabasePlugin) {
            port = properties.h2.console.port
            enabled = properties.h2.console.enabled
        }
    }
}