plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    // Ktor
    compileOnly(platform(libs.ktor.bom))
    compileOnly(libs.ktor.server.core)
    compileOnly(libs.ktor.server.content.negotiation)
    compileOnly(libs.ktor.serialization.jackson)
    // Jackson
    compileOnly(libs.jackson.module.kotlin)
    compileOnly(libs.jackson.datatype.jsr310)
    // Kafka
    compileOnly(libs.kafka.clients)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}