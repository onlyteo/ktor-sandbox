package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.config.configureJackson
import com.onlyteo.sandbox.plugin.configureDatabase
import com.onlyteo.sandbox.plugin.configureRouting
import com.onlyteo.sandbox.plugin.configureSerialization
import com.onlyteo.sandbox.plugin.configureValidation
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
            jackson { configureJackson() }
        }
    }
}