rootProject.name = "ktor-sandbox"

// MANAGEMENT
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

// PLUGINS
plugins {
    kotlin("jvm") version "2.1.21" apply false
    kotlin("plugin.spring") version "2.1.21" apply false
    kotlin("plugin.serialization") version "2.1.21" apply false
    id("io.ktor.plugin") version "3.1.2" apply false
    id("org.springframework.boot") version "3.5.0" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("com.google.cloud.tools.jib") version "3.4.5" apply false
    id("org.jooq.jooq-codegen-gradle") version "3.20.4" apply false
    id("com.gorylenko.gradle-git-properties") version "2.5.0" apply false
    id("com.expediagroup.graphql") version "8.4.0" apply false
}

// LIBS
include(":libs:async")
include(":libs:logging")
include(":libs:properties")
include(":libs:serialization")
include(":libs:database")
include(":libs:file-handling")
include(":libs:error-handling")
include(":libs:kafka-clients")
include(":libs:kafka-streams")

// APPS
include(":apps:ktor-api-rest:frontend")
include(":apps:ktor-api-rest:frontend-api")
include(":apps:ktor-api-rest:backend")

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

include(":apps:ktor-api-graphql:frontend")
include(":apps:ktor-api-graphql:frontend-api")
include(":apps:ktor-api-graphql:backend")
