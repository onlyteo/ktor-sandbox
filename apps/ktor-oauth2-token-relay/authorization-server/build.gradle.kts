plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    application
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.jdbc)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.thymeleaf)
    implementation(libs.spring.security.oauth2.authorization.server)
    implementation(libs.thymeleaf.layout.dialect)
    implementation(libs.thymeleaf.extras.spring.security)
    implementation(libs.h2.database)
    implementation(libs.bundles.webjars)
}
