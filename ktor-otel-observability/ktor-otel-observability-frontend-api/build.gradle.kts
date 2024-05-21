plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.webjars)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.ktor.client)
    testImplementation(libs.bundles.ktor.unit.test)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}