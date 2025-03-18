plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    application
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.logging)
    implementation(libs.bundles.ktor.authentication.jwt)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.ktor.webjars)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit)
}

application {
    mainClass = "com.onlyteo.sandbox.OAuth2TokenRelayBackendApplicationKt"
}