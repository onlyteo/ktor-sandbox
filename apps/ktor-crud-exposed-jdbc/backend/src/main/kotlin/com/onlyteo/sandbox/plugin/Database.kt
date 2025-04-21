package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.database.plugin.DataSourcePlugin
import com.onlyteo.sandbox.database.plugin.FlywayPlugin
import com.onlyteo.sandbox.database.plugin.H2DatabasePlugin
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase(applicationContext: ApplicationContext) {
    with(applicationContext) {
        install(DataSourcePlugin) {
            dataSource = hikariDataSource
            onApplicationStarted = { dataSource -> Database.connect(dataSource) }
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