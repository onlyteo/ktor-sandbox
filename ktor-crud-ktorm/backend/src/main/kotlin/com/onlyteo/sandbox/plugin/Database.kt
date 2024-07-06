package com.onlyteo.sandbox.plugin

import com.onlyteo.sandbox.context.ApplicationContext
import com.onlyteo.sandbox.plugin.database.FlywayPlugin
import com.onlyteo.sandbox.plugin.database.H2Plugin
import io.ktor.server.application.Application
import io.ktor.server.application.install
import javax.sql.DataSource

context(ApplicationContext)
fun Application.configureDatabase(hikariDataSource: DataSource) {

    install(FlywayPlugin) {
        dataSource = hikariDataSource
    }
    install(H2Plugin) {
        enabled = properties.h2.console.enabled
        port = properties.h2.console.port
    }
}