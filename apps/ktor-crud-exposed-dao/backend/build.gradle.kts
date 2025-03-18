plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    application
}

dependencies {
    // Project
    implementation(project(":libs:database"))
    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.logging)
    implementation(libs.bundles.ktor.webjars)
    // Other
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.h2.database.support)
    implementation(libs.jetbrains.exposed.jdbc)
    implementation(libs.jetbrains.exposed.dao)
    // Test
    testImplementation(libs.bundles.ktor.client)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
}

application {
    mainClass = "com.onlyteo.sandbox.CrudExposedDaoBackendApplicationKt"
}