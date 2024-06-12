plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.server.websockets)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.client)
    implementation(libs.ktor.client.websockets)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.kafka)
    testImplementation(libs.bundles.ktor.test)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
    testImplementation(libs.kafka.streams.test.utils)
}

application {
    mainClass.set("com.onlyteo.sandbox.KafkaStreamsFrontendApiApplicationKt")
}