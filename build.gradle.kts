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
    configurations {
        all {
            exclude(group = "junit", module = "junit")
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "21"
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
            }
        }

        withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}
