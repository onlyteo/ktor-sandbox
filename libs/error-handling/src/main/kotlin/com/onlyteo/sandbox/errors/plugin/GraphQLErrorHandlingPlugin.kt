package com.onlyteo.sandbox.errors.plugin

import com.expediagroup.graphql.server.ktor.defaultGraphQLStatusPages
import io.ktor.server.application.ApplicationPlugin
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.statuspages.StatusPages

const val GRAPHQL_ERROR_HANDLING_PLUGIN_NAME: String = "GraphQLErrorHandlingPlugin"

class GraphQLErrorHandlingPluginConfig {
}

val GraphQLErrorHandlingPlugin: ApplicationPlugin<GraphQLErrorHandlingPluginConfig> =
    createApplicationPlugin("GraphQLErrorHandlingPlugin", ::GraphQLErrorHandlingPluginConfig) {
        application.log.info("Installing {}", GRAPHQL_ERROR_HANDLING_PLUGIN_NAME)

        application.install(StatusPages) {
            defaultGraphQLStatusPages()
        }
    }