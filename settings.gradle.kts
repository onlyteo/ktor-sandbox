pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

rootProject.name = "ktor-sandbox"

include(":ktor-crud-exposed:frontend")
include(":ktor-crud-exposed:backend")

include(":ktor-oauth2-client-react:frontend")
include(":ktor-oauth2-client-react:frontend-api")
include(":ktor-oauth2-client-react:backend")
include(":ktor-oauth2-client-react:authorization-server")

include(":ktor-kafka-cqrs:frontend")
include(":ktor-kafka-cqrs:frontend-api")
include(":ktor-kafka-cqrs:backend")

include(":ktor-otel-observability:frontend")
include(":ktor-otel-observability:frontend-api")
include(":ktor-otel-observability:backend")
