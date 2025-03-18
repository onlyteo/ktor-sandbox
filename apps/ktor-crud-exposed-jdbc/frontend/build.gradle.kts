plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    application
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.logging)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.ktor.thymeleaf)
    implementation(libs.bundles.hoplite)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
}

application {
    mainClass = "com.onlyteo.sandbox.CrudExposedJdbcFrontendApplicationKt"
}