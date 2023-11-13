import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

plugins {
    kotlin("jvm")
}

val generatedPath: Provider<Directory> = layout.buildDirectory.dir("generated/main/kotlin")
sourceSets {
    main {
        java.srcDir(generatedPath)
    }
}

dependencies {
    implementation(project(":crowdproj-ad-common"))

    implementation(libs.coroutines.core)
    implementation(libs.uuid)


    implementation(libs.gremlin)
    implementation(libs.arcadedb.engine)
    implementation(libs.arcadedb.network)
    implementation(libs.arcadedb.gremlin)

    testImplementation(kotlin("test-junit"))
    testImplementation(libs.test.containers)

    testImplementation(project(":crowdproj-ad-repo-tests"))
}

val arcadeDbVersion: String = libs.versions.arcadedb.get()

tasks {
    val gradleConstants by creating {
        file(generatedPath.get().file("GradleConstants.kt")).apply {
            ensureParentDirsCreated()
            writeText(
                """
                    package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

                    const val ARCADEDB_VERSION = "$arcadeDbVersion"
                """.trimIndent()
            )
        }
    }
    compileKotlin.get().dependsOn(gradleConstants)
}
