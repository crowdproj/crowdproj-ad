import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess

plugins {
    id("backend-convention")
}

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
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.uuid)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.test.containers)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val nativeMain by getting {

        }
    }
}

tasks {
    withType(CInteropProcess::class) {
        dependsOn(project(":crowdproj-ad-rust").getTasksByName("build", false))
    }
    withType(KotlinNativeTest::class) {
        environment("LD_LIBRARY_PATH", rustLibDirX64)
    }
}

fun KotlinNativeTarget.prepareNative() {
    compilations.getByName("main") {
        cinterops {
            val testcontRust by creating
        }
    }
}
