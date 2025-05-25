package com.onlyteo.sandbox.app.context

import com.onlyteo.sandbox.app.cache.RequestCache
import com.onlyteo.sandbox.app.config.buildRequestCache
import com.onlyteo.sandbox.app.config.buildRestClient
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.app.service.AuthService
import com.onlyteo.sandbox.app.service.GreetingService
import io.ktor.client.HttpClient

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val httpClient: HttpClient = buildRestClient(),
    val requestCache: RequestCache<String, String> = buildRequestCache(),
    val authService: AuthService = AuthService(properties, httpClient),
    val greetingService: GreetingService = GreetingService(properties, httpClient)
)
