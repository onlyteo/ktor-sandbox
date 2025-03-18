plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    application
}

dependencies {
    // Project
    implementation(project(":libs:serialization"))
    implementation(project(":libs:error-handling"))
    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.logging)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.ktor.thymeleaf)
    // Other
    implementation(libs.bundles.hoplite)
    // Test
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
}

application {
    mainClass = "com.onlyteo.sandbox.CrudExposedDaoFrontendApplicationKt"
}