import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependencies) apply false
}

allprojects {
    group = "com.onlyteo.sandbox"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs += "-Xjsr305=strict"
                jvmTarget = "21"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}
