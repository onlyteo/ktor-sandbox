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

include(":ktor-oauth2-react-client:ktor-oauth2-react-client-frontend")
include(":ktor-oauth2-react-client:ktor-oauth2-react-client-frontend-api")
include(":ktor-oauth2-react-client:ktor-oauth2-react-client-backend")
include(":ktor-oauth2-react-client:ktor-oauth2-react-client-authorization-server")

include(":ktor-kafka-streams:ktor-kafka-streams-frontend")
include(":ktor-kafka-streams:ktor-kafka-streams-frontend-api")
include(":ktor-kafka-streams:ktor-kafka-streams-backend")
