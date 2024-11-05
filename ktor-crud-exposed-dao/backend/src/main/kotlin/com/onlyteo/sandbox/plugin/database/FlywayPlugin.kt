package com.onlyteo.sandbox.plugin.database

import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import org.flywaydb.core.Flyway
import javax.sql.DataSource

val FlywayMigrationCompleted: EventDefinition<Application> = EventDefinition()

@KtorDsl
class FlywayPluginConfig {
    var dataSource: DataSource? = null
}

val FlywayPlugin: ApplicationPlugin<FlywayPluginConfig> =
    createApplicationPlugin("FlywayPlugin", ::FlywayPluginConfig) {
        val dataSource = checkNotNull(pluginConfig.dataSource) { "Data source must not be null" }
        application.log.info("FlywayPlugin initialized")

        on(MonitoringEvent(DataSourceReady)) { application ->
            application.log.info("Executing database migration")
            Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .load()
                .migrate()
            application.monitor.raise(FlywayMigrationCompleted, application)
        }
    }
