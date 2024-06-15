plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.google.cloud.jib)
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.ktor.micrometer)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    testImplementation(libs.bundles.ktor.test)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
}

application {
    mainClass = "com.onlyteo.sandbox.OtelObservabilityFrontendApiApplicationKt"
}

jib {
    from {
        image = "eclipse-temurin:21-jre-alpine"
    }
    to {
        image = "sandbox.ktor-otel-observability-frontend-api:latest"
    }
    container {
        mainClass = "com.onlyteo.sandbox.OtelObservabilityFrontendApiApplicationKt"
        jvmFlags = listOf("-Xms512m", "-Xmx1024m")
        environment = mapOf("KTOR_ENV" to "production")
        ports = listOf("8080")
    }
}