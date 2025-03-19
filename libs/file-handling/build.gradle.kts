plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    // Jackson
    compileOnly(libs.jackson.module.kotlin)
    compileOnly(libs.jackson.dataformat.csv)
}
