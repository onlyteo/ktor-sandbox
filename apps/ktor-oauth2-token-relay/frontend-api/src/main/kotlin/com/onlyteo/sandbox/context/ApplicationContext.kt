package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.cache.RequestCache
import com.onlyteo.sandbox.config.buildRequestCache
import com.onlyteo.sandbox.config.buildRestClient
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.service.AuthService
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.client.HttpClient

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val httpClient: HttpClient = buildRestClient(),
    val requestCache: RequestCache<String, String> = buildRequestCache(),
    val authService: AuthService = AuthService(properties, httpClient),
    val greetingService: GreetingService = GreetingService(properties, httpClient)
)
