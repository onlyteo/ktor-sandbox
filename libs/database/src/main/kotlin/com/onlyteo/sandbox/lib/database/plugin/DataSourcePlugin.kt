package com.onlyteo.sandbox.lib.database.plugin

import io.ktor.events.EventDefinition
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.ktor.server.application.log
import io.ktor.utils.io.KtorDsl
import javax.sql.DataSource

const val DATA_SOURCE_PLUGIN_NAME = "DataSourcePlugin"
val DataSourceReady: EventDefinition<Application> = EventDefinition()
val DataSourceClosing: EventDefinition<Application> = EventDefinition()

@KtorDsl
class DataSourcePluginConfig {
    var dataSource: DataSource? = null
    var onApplicationStarted: (DataSource) -> Unit = {}
}

val DataSourcePlugin: ApplicationPlugin<DataSourcePluginConfig> =
    createApplicationPlugin(DATA_SOURCE_PLUGIN_NAME, ::DataSourcePluginConfig) {
        val dataSource = checkNotNull(pluginConfig.dataSource) { "Data source must not be null" }
        val onApplicationStarted = pluginConfig.onApplicationStarted
        application.log.info("DataSourcePlugin initialized")

        on(MonitoringEvent(ApplicationStarted)) { application ->
            application.log.info("Connecting to data source")
            onApplicationStarted(dataSource)
            application.log.info("Completed connection to data source")
            application.monitor.raise(DataSourceReady, application)
        }

        on(MonitoringEvent(ApplicationStopping)) { application ->
            application.log.info("Closing data source")
            application.monitor.raise(DataSourceClosing, application)
            dataSource.connection.close()
        }
    }