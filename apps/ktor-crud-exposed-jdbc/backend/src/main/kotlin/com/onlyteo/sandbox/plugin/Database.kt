package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.database.plugin.FlywayPlugin
import com.onlyteo.sandbox.database.plugin.H2DatabasePlugin
import com.onlyteo.sandbox.plugin.custom.DataSourcePlugin
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureDatabase(applicationContext: ApplicationContext) {
    with(applicationContext) {
        install(DataSourcePlugin) {
            dataSource = hikariDataSource
        }
        install(FlywayPlugin) {
            dataSource = hikariDataSource
        }
        install(H2DatabasePlugin) {
            enabled = properties.h2.console.enabled
            port = properties.h2.console.port
        }
    }
}