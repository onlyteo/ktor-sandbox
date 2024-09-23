package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.properties.ApplicationProperties
import com.onlyteo.sandbox.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.repository.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val prefixRepository: PrefixRepository = PrefixRepository(properties.resources.prefixesFile),
    val greetingService: GreetingService = GreetingService(prefixRepository)
)
