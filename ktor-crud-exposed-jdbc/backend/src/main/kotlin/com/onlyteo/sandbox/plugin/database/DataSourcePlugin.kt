package com.onlyteo.sandbox.plugin.database

import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.util.KtorDsl
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

val DatabaseReady: EventDefinition<Application> = EventDefinition()

@KtorDsl
class DataSourcePluginConfig {
    var dataSource: DataSource? = null
}

val DataSourcePlugin: ApplicationPlugin<DataSourcePluginConfig> =
    createApplicationPlugin("DataSourcePlugin", ::DataSourcePluginConfig) {
        val dataSource = checkNotNull(pluginConfig.dataSource) { "Data source must not be null" }

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Initializing data source")
            Database.connect(dataSource)
            this@createApplicationPlugin.application.environment.monitor.raise(DatabaseReady, application)
        }
    }