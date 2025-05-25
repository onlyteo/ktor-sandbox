package com.onlyteo.sandbox.app.context

import com.onlyteo.sandbox.app.properties.ApplicationProperties
import com.onlyteo.sandbox.app.properties.ApplicationPropertiesHolder
import com.onlyteo.sandbox.properties.loadProperties
import com.onlyteo.sandbox.app.repository.PrefixRepository
import com.onlyteo.sandbox.app.service.GreetingService

data class ApplicationContext(
    val properties: ApplicationProperties = loadProperties<ApplicationPropertiesHolder>().app,
    val prefixRepository: PrefixRepository = PrefixRepository(properties.resources.prefixesFile),
    val greetingService: GreetingService = GreetingService(prefixRepository),
)
