package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.repository.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val prometheusMeterRegistry: PrometheusMeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT),
    val prefixRepository: PrefixRepository = PrefixRepository(properties.resources.prefixesFile),
    val greetingService: GreetingService = GreetingService(prefixRepository)
)
