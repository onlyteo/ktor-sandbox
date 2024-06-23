import java.time.Instant

val jvmMajorVersion: String by project
val jvmVersion = JavaVersion.toVersion(jvmMajorVersion)
val agents by configurations.creating

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.google.cloud.jib)
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.ktor.logging)
    implementation(libs.bundles.ktor.micrometer)
    implementation(libs.bundles.ktor.opentelemetry)
    implementation(libs.bundles.hoplite)
    testImplementation(libs.bundles.ktor.test)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
    agents(libs.opentelemetry.java.agent)
}

application {
    mainClass = "com.onlyteo.sandbox.OtelObservabilityFrontendApiApplicationKt"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jvmVersion.toString()))
    }
}

tasks.withType(Jar::class) {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
    }
}

tasks.register<Copy>("copyAgents") {
    from(agents)
    into("${layout.buildDirectory.get()}/agents")
}

tasks.named("assemble") {
    finalizedBy("copyAgents")
}

jib {
    from {
        image = "eclipse-temurin:${jvmVersion}-jre-alpine"
    }
    to {
        image = "sandbox.${project.name}:latest"
    }
    container {
        mainClass = application.mainClass.get()
        jvmFlags = listOf(
            "-Xms256m",
            "-Xmx512m",
            "-javaagent:/app/agents/opentelemetry-javaagent-${libs.versions.opentelemetry.instrumentation}.jar"
        )
        environment = mapOf(
            "KTOR_ENV" to "production",
            "OTEL_EXPORTER_OTLP_ENDPOINT" to "http://sandbox.otel-collector:4317",
            "OTEL_EXPORTER_OTLP_PROTOCOL" to "grpc",
            "OTEL_EXPORTER_OTLP_INSECURE" to "true",
            "OTEL_METRICS_EXPORTER" to "none",
            "OTEL_LOGS_EXPORTER" to "none",
            "OTEL_SERVICE_NAME" to "sandbox.${project.name}"
        )
        ports = listOf("8080")
        creationTime.set(Instant.now().toString())
        extraDirectories {
            paths {
                path {
                    setFrom("${layout.buildDirectory.get()}/agents")
                    into = "${jib.container.appRoot}/agents"
                }
            }
        }
    }
}
