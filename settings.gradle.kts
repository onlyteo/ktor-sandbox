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
include(":ktor-oauth2-react-client:ktor-oauth2-react-client-authorization-server")
