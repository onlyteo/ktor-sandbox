val jvmMajorVersion: String by project
val jvmVersion = JavaVersion.toVersion(jvmMajorVersion)

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.jooq.codegen)
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.serialization)
    implementation(libs.bundles.ktor.logging)
    implementation(libs.bundles.ktor.webjars)
    implementation(libs.bundles.logback)
    implementation(libs.bundles.hoplite)
    implementation(libs.jackson.dataformat.csv)
    implementation(libs.bundles.h2.database.support)
    implementation(libs.jooq)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)

    jooqCodegen(libs.jooq.meta.extensions)
}

application {
    mainClass = "com.onlyteo.sandbox.CrudJooqBackendApplicationKt"
}

sourceSets {
    main {
        kotlin.srcDirs("${layout.buildDirectory.get()}/generated/kotlin")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jvmVersion.toString()))
    }
}

jooq {
    configuration {
        generator {
            name = "org.jooq.codegen.KotlinGenerator"
            database {
                name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                properties {
                    property {
                        key = "scripts"
                        value = "src/main/resources/db/migration/*.sql"
                    }
                    property {
                        key = "sort"
                        value = "semantic"
                    }
                    property {
                        key = "unqualifiedSchema"
                        value = "none"
                    }
                    property {
                        key = "defaultNameCase"
                        value = "as_is"
                    }
                }
            }
            generate {
                withDeprecated(false)
                withPojosAsKotlinDataClasses(true)
                withFluentSetters(true)
                withKotlinSetterJvmNameAnnotationsOnIsPrefix(true)
            }
            target {
                encoding = "UTF-8"
                packageName = "com.onlyteo.sandbox.model"
                directory = "${layout.buildDirectory.get()}/generated/kotlin"
            }
            strategy {
                name = "org.jooq.codegen.DefaultGeneratorStrategy"
            }
        }
    }
}

tasks.compileKotlin.configure {
    dependsOn(tasks.jooqCodegen)
}