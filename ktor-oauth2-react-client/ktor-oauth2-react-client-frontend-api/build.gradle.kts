plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.authentication)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.hoplite)
    testImplementation(libs.bundles.ktor.test)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}