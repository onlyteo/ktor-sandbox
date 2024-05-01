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

include(":ktor-oauth2-react-client:frontend")
include(":ktor-oauth2-react-client:frontend-api")
include(":ktor-oauth2-react-client:authorization-server")
project(":ktor-oauth2-react-client:frontend").name = "ktor-oauth2-react-client-frontend"
project(":ktor-oauth2-react-client:frontend-api").name = "ktor-oauth2-react-client-frontend-api"
project(":ktor-oauth2-react-client:authorization-server").name = "ktor-oauth2-react-client-authorization-server"
