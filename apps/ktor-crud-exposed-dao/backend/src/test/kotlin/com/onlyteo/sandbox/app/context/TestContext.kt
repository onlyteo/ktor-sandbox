package com.onlyteo.sandbox.app.context

import com.onlyteo.sandbox.app.plugin.configureDatabase
import com.onlyteo.sandbox.app.plugin.configureRouting
import com.onlyteo.sandbox.app.plugin.configureValidation
import com.onlyteo.sandbox.lib.serialization.factory.configureJackson
import com.onlyteo.sandbox.lib.serialization.plugin.configureSerialization
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.ApplicationTestBuilder

class TestContext {

    private val applicationContext = ApplicationContext()

    fun ApplicationTestBuilder.configureServer() {
        application {
            configureSerialization()
            configureValidation()
            //configureErrorHandling()
            configureDatabase(applicationContext)
            configureRouting(applicationContext)
        }
    }

    fun HttpClientConfig<*>.configureClient() {
        install(ContentNegotiation) {
            jackson {
                configureJackson()
            }
        }
    }
}