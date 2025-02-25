package com.onlyteo.sandbox.properties

data class ApplicationPropertiesHolder(
    val app: ApplicationProperties
)

data class ApplicationProperties(
    val resources: ResourcesProperties,
    val dataSource: DataSourceProperties,
    val h2: H2Properties
)

data class ResourcesProperties(
    val prefixesFile: String
)

data class H2Properties(
    val console: H2ConsoleProperties
)

data class H2ConsoleProperties(
    val enabled: Boolean,
    val port: Int? = -1
)
