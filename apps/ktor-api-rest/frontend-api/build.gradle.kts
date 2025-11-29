plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    application
}

dependencies {
    // Project
    implementation(project(":libs:logging"))
    implementation(project(":libs:properties"))
    implementation(project(":libs:serialization"))
    implementation(project(":libs:error-handling"))
    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization.jackson)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.logging)
    // Other
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.bundles.ktor.webjars)
    // Test
    testImplementation(libs.ktor.server.test.host)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit)
}

application {
    mainClass = "com.onlyteo.sandbox.RestApiFrontendApiApplicationKt"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}