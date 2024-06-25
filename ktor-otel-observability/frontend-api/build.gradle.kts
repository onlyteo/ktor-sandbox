import java.time.Instant

val jvmMajorVersion: String by project
val jvmVersion = JavaVersion.toVersion(jvmMajorVersion)
val agents by configurations.creating
val opentelemetryJavaAgent =
    "${libs.opentelemetry.java.agent.get().name}-${libs.opentelemetry.java.agent.get().version}.jar"

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

tasks.jib.configure {
    dependsOn("copyAgents")
}
tasks.jibDockerBuild.configure {
    dependsOn("copyAgents")
}
tasks.jibBuildTar.configure {
    dependsOn("copyAgents")
}

jib {
    from {
        image = "eclipse-temurin:${jvmVersion}-jre-alpine"
    }
    to {
        image = "sandbox.ktor-otel-observability-${project.name}:latest"
    }
    container {
        appRoot = "/app" // Define app root dir to be able to use it later on
        mainClass = application.mainClass.get()
        jvmFlags = listOf(
            "-Xms256m",
            "-Xmx512m",
            "-javaagent:${jib.container.appRoot}/agents/${opentelemetryJavaAgent}"
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
