plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    application
}

dependencies {
    // Project
    implementation(project(":libs:async"))
    implementation(project(":libs:serialization"))
    implementation(project(":libs:error-handling"))
    implementation(project(":libs:kafka-clients"))
    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.server.websockets)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.logging)
    implementation(libs.ktor.client.websockets)
    // Other
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.kafka)
    // Test
    testImplementation(libs.ktor.server.test.host)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
    testImplementation(libs.kafka.streams.test.utils)
}

application {
    mainClass = "com.onlyteo.sandbox.KafkaCqrsKafkaFrontendApiApplicationKt"
}