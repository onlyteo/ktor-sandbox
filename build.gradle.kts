import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ktor) apply false
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
