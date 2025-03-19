plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    compileOnly(libs.bundles.logback)
}
