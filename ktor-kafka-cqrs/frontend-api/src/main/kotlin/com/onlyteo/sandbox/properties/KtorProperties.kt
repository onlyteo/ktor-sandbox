package com.onlyteo.sandbox.properties

const val KTOR_PROPERTIES_FILE = "/application.yaml"

data class KtorPropertiesHolder(val ktor: KtorProperties)

data class KtorProperties(val deployment: DeploymentProperties)

data class DeploymentProperties(val host: String, val port: Int)
