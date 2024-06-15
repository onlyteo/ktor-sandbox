import com.gorylenko.GitPropertiesPluginExtension

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.google.cloud.jib)
    alias(libs.plugins.git.properties)
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.ktor.micrometer)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    testImplementation(libs.bundles.ktor.test)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
}

application {
    mainClass = "com.onlyteo.sandbox.OtelObservabilityBackendApplicationKt"
}

configure<GitPropertiesPluginExtension> {
    extProperty = "git"
}

//val gitCommitTime: Provider<String> = project.provider { project.ext.get("git.commit.time") }

for (prop in project.ext.properties) {
    println(prop.toString())
}

jib {
    from {
        image = "eclipse-temurin:21-jre-alpine"
    }
    to {
        image = "sandbox.ktor-otel-observability-backend:latest"
    }
    container {
        mainClass = "com.onlyteo.sandbox.OtelObservabilityBackendApplicationKt"
        jvmFlags = listOf("-Xms512m", "-Xmx1024m")
        environment = mapOf("KTOR_ENV" to "production")
        ports = listOf("8081")
        //     creationTime.set(gitCommitTime) // TODO Set to most recent git commit time
    }
}