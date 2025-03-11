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

include(":apps:ktor-rest-api:frontend")
include(":apps:ktor-rest-api:frontend-api")
include(":apps:ktor-rest-api:backend")

include(":apps:ktor-crud-exposed-jdbc:frontend")
include(":apps:ktor-crud-exposed-jdbc:backend")

include(":apps:ktor-crud-exposed-dao:frontend")
include(":apps:ktor-crud-exposed-dao:backend")

include(":apps:ktor-crud-ktorm:frontend")
include(":apps:ktor-crud-ktorm:backend")

include(":apps:ktor-crud-jooq:frontend")
include(":apps:ktor-crud-jooq:backend")

include(":apps:ktor-oauth2-token-relay:frontend")
include(":apps:ktor-oauth2-token-relay:frontend-api")
include(":apps:ktor-oauth2-token-relay:backend")
include(":apps:ktor-oauth2-token-relay:authorization-server")

include(":apps:ktor-cqrs-kafka:frontend")
include(":apps:ktor-cqrs-kafka:frontend-api")
include(":apps:ktor-cqrs-kafka:backend")

include(":apps:ktor-otel-observability:frontend")
include(":apps:ktor-otel-observability:frontend-api")
include(":apps:ktor-otel-observability:backend")
