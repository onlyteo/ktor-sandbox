package com.onlyteo.sandbox.context

import com.onlyteo.sandbox.properties.ApplicationProperties
import org.slf4j.Logger

data class ApplicationContext(
    val logger: Logger,
    val applicationProperties: ApplicationProperties
)
