import com.expediagroup.graphql.plugin.gradle.config.GraphQLSerializer
import com.expediagroup.graphql.plugin.gradle.graphql

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
    implementation(project(":libs:error-handling"))
    // Ktor
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.serialization.jackson)
    implementation(libs.bundles.ktor.logging)
    // Other
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.graphql.kotlin.ktor.server) {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "io.ktor")
        exclude(group = "com.fasterxml.jackson.module")
    }
    implementation(libs.graphql.kotlin.ktor.client) {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "io.ktor")
        exclude(group = "com.fasterxml.jackson.module")
        exclude(group = "com.expediagroup", module = "graphql-kotlin-client-serialization")
    }
    implementation(libs.graphql.kotlin.client.jackson) {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "com.fasterxml.jackson.module")
    }
    // Test
    testImplementation(libs.ktor.server.test.host)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit)
}

application {
    mainClass = "com.onlyteo.sandbox.GraphQLApiFrontendApiApplicationKt"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

graphql {
    schema {
        packages = listOf("com.onlyteo.sandbox")

    }
    client {
        packageName = "com.onlyteo.sandbox.schema"
        schemaFile = file("src/main/resources/schema/schema.graphqls")
        queryFiles = file("src/main/resources/schema/model").listFiles()?.toList().orEmpty()
        serializer = GraphQLSerializer.JACKSON
    }
}