package com.onlyteo.sandbox.environment

import com.sksamuel.hoplite.env.Environment

fun currentEnvironment(): Environment {
    val developmentFlag = System.getProperty("io.ktor.development")
    if (developmentFlag == "true") {
        return Environment.development
    }
    return Environment.fromEnvVar("KTOR_ENV", Environment.development)
}
