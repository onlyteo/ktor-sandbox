plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.kafka)
    testImplementation(libs.bundles.ktor.test)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}