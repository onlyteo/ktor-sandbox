package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.config.buildHikariDataSource
import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.database.DataSourcePlugin
import com.onlyteo.sandbox.plugin.database.FlywayPlugin
import com.onlyteo.sandbox.plugin.database.H2Plugin
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureDatabase(applicationContext: ApplicationContext) {
    with(applicationContext) {
        val hikariDataSource = properties.dataSource.buildHikariDataSource()

        install(DataSourcePlugin) {
            dataSource = hikariDataSource
        }
        install(FlywayPlugin) {
            dataSource = hikariDataSource
        }
        install(H2Plugin) {
            enabled = properties.h2.console.enabled
            port = properties.h2.console.port
        }
    }
}