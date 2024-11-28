package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.config.loadProperties
import com.onlyteo.sandbox.properties.AppProperties
import com.onlyteo.sandbox.properties.AppPropertiesHolder
import com.onlyteo.sandbox.repository.PrefixRepository
import com.onlyteo.sandbox.service.GreetingService

data class ApplicationContext(
    val properties: AppProperties = loadProperties<AppPropertiesHolder>().app,
    val prefixRepository: PrefixRepository = PrefixRepository(properties.resources.prefixesFile),
    val greetingService: GreetingService = GreetingService(prefixRepository),
)
