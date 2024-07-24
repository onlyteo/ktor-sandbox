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
}

rootProject.name = "ktor-sandbox"

include(":ktor-crud-exposed:frontend")
include(":ktor-crud-exposed:backend")

include(":ktor-crud-ktorm:frontend")
include(":ktor-crud-ktorm:backend")

include(":ktor-crud-jooq:frontend")
include(":ktor-crud-jooq:backend")

include(":ktor-oauth2-token-relay:frontend")
include(":ktor-oauth2-token-relay:frontend-api")
include(":ktor-oauth2-token-relay:backend")
include(":ktor-oauth2-token-relay:authorization-server")

include(":ktor-kafka-cqrs:frontend")
include(":ktor-kafka-cqrs:frontend-api")
include(":ktor-kafka-cqrs:backend")

include(":ktor-otel-observability:frontend")
include(":ktor-otel-observability:frontend-api")
include(":ktor-otel-observability:backend")
