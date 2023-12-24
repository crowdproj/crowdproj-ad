import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("backend-convention")
    alias(libs.plugins.kotlinx.serialization)
}

val generatedPath: Provider<Directory> = layout.buildDirectory.dir("generated/main/kotlin")
kotlin {
    jvm { withJava() }
    linuxArm64 {
        prepareNative()
    }
    linuxX64 {
        prepareNative()
    }

    sourceSets {
        commonMain {
            kotlin.srcDirs(generatedPath)
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.uuid)

                implementation(project(":crowdproj-ad-common"))
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.test.containers)
                implementation(project(":crowdproj-ad-repo-tests"))

                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.test.containers)
            }
        }
    }
}
val arcadeDbVersion: String = libs.versions.arcadedb.get()

tasks {
    val gradleConstants by creating {
        file(generatedPath.get().file("GradleConstants.kt")).apply {
            ensureParentDirsCreated()
            writeText(
                """
                    package ${project.group}.backend.repo.gremlin

                    const val ARCADEDB_VERSION = "$arcadeDbVersion"
                """.trimIndent()
            )
        }
    }
    withType(KotlinCompile::class.java) {
        dependsOn(gradleConstants)
    }
}

fun KotlinNativeTarget.prepareNative() {
    compilations.getByName("main") {
//        cinterops {
//            val gremlinRust by creating
//        }
    }
}
