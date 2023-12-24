import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("backend-convention")
}

val generatedPath: Provider<Directory> = layout.buildDirectory.dir("generated/main/kotlin")

val rustDir: Directory = project(":crowdproj-ad-rust").layout.projectDirectory
val rustTargetDir: String by project
val rustIncludesDir: String by project
val rustLibDirX64: String by project
val rustLibDirArm64: String by project
val rustLibDirX64Debug: String by project
val rustLibDirArm64Debug: String by project

kotlin {
    jvm { withJava() }
    linuxArm64 {
        prepareNative()
    }
    linuxX64 {
        prepareNative()
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs(generatedPath)
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.uuid)

                implementation(project(":crowdproj-ad-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.test.containers)
                implementation(project(":crowdproj-ad-repo-tests"))

                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.gremlin)
                implementation(libs.arcadedb.engine)
                implementation(libs.arcadedb.network)
                implementation(libs.arcadedb.gremlin)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.test.containers)
            }
        }
        val nativeMain by getting {

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
    withType(CInteropProcess::class) {
        dependsOn(gradleConstants)
        dependsOn(project(":crowdproj-ad-rust").getTasksByName("build", false))
        inputs.file(rustDir.dir(rustIncludesDir).file("cwp-ad-repo-gremlin-rust.h"))
    }
    withType(KotlinNativeTest::class) {
        environment("LD_LIBRARY_PATH", rustDir.dir(rustLibDirX64).toString())
        environment("RUST_BACKTRACE","full")
    }
}

fun KotlinNativeTarget.prepareNative() {
    compilations.getByName("main") {
        cinterops {
            val gremlinRust by creating
        }
    }
}
