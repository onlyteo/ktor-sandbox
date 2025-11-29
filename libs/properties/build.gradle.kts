plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    compileOnly(libs.hoplite.yaml)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}