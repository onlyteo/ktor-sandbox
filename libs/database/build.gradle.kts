plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    // Ktor
    compileOnly(platform(libs.ktor.bom))
    compileOnly(libs.ktor.server.core)
    // Other
    compileOnly(libs.flyway.core)
    compileOnly(libs.h2.database)
    compileOnly(libs.zaxxer.hikari.cp)
}
