package com.onlyteo.sandbox.database.plugin

import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import org.flywaydb.core.api.configuration.FluentConfiguration
import javax.sql.DataSource

const val FLYWAY_PLUGIN_NAME = "FlywayPlugin"

@KtorDsl
class FlywayPluginConfig {
    var dataSource: DataSource? = null
}

val FlywayPlugin: ApplicationPlugin<FlywayPluginConfig> =
    createApplicationPlugin(FLYWAY_PLUGIN_NAME, ::FlywayPluginConfig) {
        application.log.info("Installing {}", FLYWAY_PLUGIN_NAME)
        val dataSource = checkNotNull(pluginConfig.dataSource) { "Data source must not be null" }

        val flyway = FluentConfiguration()
            .dataSource(dataSource)
            .load()

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Executing database migration")
            flyway.migrate()
        }
    }
