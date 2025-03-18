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
}
