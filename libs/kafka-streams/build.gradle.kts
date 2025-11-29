plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    compileOnly(project(":libs:serialization"))
    // Ktor
    compileOnly(platform(libs.ktor.bom))
    compileOnly(libs.ktor.server.core)
    // Kafka
    compileOnly(libs.kafka.streams)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}