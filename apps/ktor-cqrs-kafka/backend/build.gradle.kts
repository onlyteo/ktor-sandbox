plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    application
}

dependencies {
    // Project
    implementation(project(":libs:properties"))
    implementation(project(":libs:serialization"))
    implementation(project(":libs:kafka-streams"))
    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.webjars)
    // Other
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.kafka)
    // Test
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
}

application {
    mainClass = "com.onlyteo.sandbox.KafkaCqrsKafkaBackendApplicationKt"
}