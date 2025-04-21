plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    // Ktor
    compileOnly(platform(libs.ktor.bom))
    compileOnly(libs.ktor.server.core)
    compileOnly(libs.ktor.server.status.pages)
    compileOnly(libs.ktor.server.request.validation)
    compileOnly(libs.graphql.kotlin.ktor.server) {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "io.ktor")
        exclude(group = "com.fasterxml.jackson.module")
    }
}
