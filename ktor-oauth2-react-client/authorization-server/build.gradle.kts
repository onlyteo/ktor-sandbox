plugins {
    application
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktor)
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}

application {
    mainClass.set("com.onlyteo.sandbox.ApplicationKt")
}
