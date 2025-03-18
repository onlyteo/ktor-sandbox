plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    // Ktor
    compileOnly(platform(libs.ktor.bom))
    compileOnly(libs.ktor.server.core)
}
