plugins {
    kotlin("jvm")
}

dependencies {
    // Jackson
    compileOnly(libs.jackson.module.kotlin)
    compileOnly(libs.jackson.datatype.jsr310)
    // Kafka
    compileOnly(libs.kafka.clients)
}
