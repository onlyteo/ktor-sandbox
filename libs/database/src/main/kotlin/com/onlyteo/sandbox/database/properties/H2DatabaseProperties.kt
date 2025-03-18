package com.onlyteo.sandbox.database.properties

data class H2DatabaseProperties(
    val console: H2ConsoleProperties
)

data class H2ConsoleProperties(
    val enabled: Boolean = true,
    val port: Int? = -1
)
