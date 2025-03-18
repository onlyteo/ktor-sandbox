plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    compileOnly(platform(libs.ktor.bom))
    compileOnly(libs.ktor.server.core)
    compileOnly(libs.flyway.core)
    compileOnly(libs.h2.database)
    compileOnly(libs.zaxxer.hikari.cp)
}
