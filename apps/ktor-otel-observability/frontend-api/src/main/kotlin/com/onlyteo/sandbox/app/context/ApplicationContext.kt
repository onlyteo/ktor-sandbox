package com.onlyteo.sandbox.app.context

import com.onlyteo.sandbox.app.config.buildRestClient
import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.app.service.GreetingService
import io.ktor.client.HttpClient
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val prometheusMeterRegistry: PrometheusMeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT),
    val httpClient: HttpClient = buildRestClient(),
    val greetingService: GreetingService = GreetingService(properties, httpClient)
)
