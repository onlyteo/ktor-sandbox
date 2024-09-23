package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.config.buildRestClient
import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.service.GreetingService
import io.ktor.client.HttpClient
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val prometheusMeterRegistry: PrometheusMeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT),
    val httpClient: HttpClient = buildRestClient(),
    val greetingService: GreetingService = GreetingService(properties, httpClient)
)
