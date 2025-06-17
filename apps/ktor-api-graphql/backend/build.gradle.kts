plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    id("com.expediagroup.graphql")
    application
}

dependencies {
    // Project
    implementation(project(":libs:logging"))
    implementation(project(":libs:properties"))
    implementation(project(":libs:serialization"))
    implementation(project(":libs:file-handling"))
    implementation(project(":libs:error-handling"))
    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization.jackson)
    implementation(libs.bundles.ktor.logging)
    // Other
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.graphql.kotlin.ktor.server) {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "io.ktor")
        exclude(group = "com.fasterxml.jackson.module")
    }
    // Test
    testImplementation(libs.ktor.server.test.host)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit)
}

application {
    mainClass = "com.onlyteo.sandbox.GraphQLApiBackendApplicationKt"
}

graphql {
    schema {
        packages = listOf("com.onlyteo.sandbox")
    }
}
