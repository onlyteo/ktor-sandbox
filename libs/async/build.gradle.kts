plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    // Ktor
    compileOnly(libs.kotlinx.coroutines.core)
    compileOnly(libs.logback.classic)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}