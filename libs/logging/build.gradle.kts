plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    // Ktor
    compileOnly(platform(libs.ktor.bom))
    compileOnly(libs.ktor.server.core)
    compileOnly(libs.ktor.server.call.logging)
    // Logback
    compileOnly(libs.bundles.logback)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}