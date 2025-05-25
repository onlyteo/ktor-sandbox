package com.onlyteo.sandbox.app.properties

import com.onlyteo.sandbox.lib.database.properties.DataSourceProperties
import com.onlyteo.sandbox.lib.database.properties.H2DatabaseProperties

data class ApplicationPropertiesHolder(
    val app: ApplicationProperties
)

data class ApplicationProperties(
    val resources: ResourcesProperties,
    val dataSource: DataSourceProperties,
    val h2: H2DatabaseProperties
)

data class ResourcesProperties(
    val prefixesFile: String
)
