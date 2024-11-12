package com.onlyteo.sandbox.plugin.custom

import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

val DataSourceReady: EventDefinition<Application> = EventDefinition()
val DataSourceClosing: EventDefinition<Application> = EventDefinition()

@KtorDsl
class DataSourcePluginConfig {
    var dataSource: DataSource? = null
}

val DataSourcePlugin: ApplicationPlugin<DataSourcePluginConfig> =
    createApplicationPlugin("DataSourcePlugin", ::DataSourcePluginConfig) {
        val dataSource = checkNotNull(pluginConfig.dataSource) { "Data source must not be null" }
        application.log.info("DataSourcePlugin initialized")

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Connecting to data source")
            Database.connect(dataSource)
            application.log.info("Completed connection to data source")
            application.monitor.raise(DataSourceReady, application)
        }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.log.info("Closing data source")
            application.monitor.raise(DataSourceClosing, application)
            dataSource.connection.close()
        }
    }