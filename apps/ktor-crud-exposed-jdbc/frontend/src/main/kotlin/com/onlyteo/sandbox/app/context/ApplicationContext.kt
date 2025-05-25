package com.onlyteo.sandbox.app.context

import com.onlyteo.sandbox.app.config.buildRestClient
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.app.service.GreetingService
import io.ktor.client.HttpClient

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val httpClient: HttpClient = buildRestClient(),
    val greetingService: GreetingService = GreetingService(properties, httpClient)
)
