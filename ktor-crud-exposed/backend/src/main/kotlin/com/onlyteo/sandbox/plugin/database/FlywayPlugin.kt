package com.onlyteo.sandbox.plugin.database

import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.util.KtorDsl
import org.flywaydb.core.api.configuration.FluentConfiguration
import javax.sql.DataSource


@KtorDsl
class FlywayPluginConfig {
    var dataSource: DataSource? = null
}

val FlywayPlugin: ApplicationPlugin<FlywayPluginConfig> =
    createApplicationPlugin("FlywayPlugin", ::FlywayPluginConfig) {
        val dataSource = checkNotNull(pluginConfig.dataSource) { "Data source must not be null" }

        val flyway = FluentConfiguration()
            .dataSource(dataSource)
            .load()

        on(MonitoringEvent(DatabaseReady)) { application ->
            application.log.info("Executing database migration")
            flyway.migrate()
        }
    }
