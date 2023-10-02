plugins {
    kotlin("multiplatform")
}

version = rootProject.version
val rustDir = "${layout.projectDirectory}/src/rust"
val rustBuildDir = "${layout.buildDirectory.get()}/rust"

kotlin {
    @Suppress("OPT_IN_USAGE")
    targetHierarchy.default {
        group("native") {
            withLinuxArm64()
            withLinuxX64()
        }
    }
    jvm {}
    linuxX64 {
        val main by compilations.getting
        val rust by main.cinterops.creating {
            this.includeDirs(
                "$rustDir/lib"
            )
        }
//        {
//            val rustLibsPath = builtFiles
////            packageName("${project.group}")
//            includeDirs {
//                allHeaders(files(rustLibsPath))
//            }
////            headers(
////                "$goLibsPath/libcrowdproj-ad-go.h"
////            )
////            linkerOpts.addAll(
////                listOf(
////                    "-L$goLibsPath",
////                    "-l:libcrowdproj-ad-go.a"
////                )
////            )
//        }

    }
    linuxArm64 {
        val main by compilations.getting
        val rust by main.cinterops.creating
    }

    sourceSets {
        val datetimeVersion: String by project
        val coroutinesVersion: String by project
        val testContainersVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.testcontainers:testcontainers:$testContainersVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

val rustBuildTask: Task by tasks.creating {
    outputs.dir(rustBuildDir)
    val targetPlatforms = listOf(
        "x86_64-unknown-linux-gnu",
        "aarch64-unknown-linux-gnu",
    )
    doLast {
        targetPlatforms.forEach { platform ->
            exec {
                workingDir = file(rustDir)
                commandLine(
                    "make",
                    "build",
                    "PROJECT_VERSION=${project.version}",
                    "PROJECT_NAME=${project.name}.a",
                    "PROJECT_BUILD_DIR=$rustBuildDir",
                    "PLATFORM=$platform",
                )
            }
        }
    }
}

tasks {
    compileKotlinMetadata {
        dependsOn(rustBuildTask)
    }
    getByName("cinteropRustLinuxX64") {
        dependsOn(rustBuildTask)
    }
    getByName("cinteropRustLinuxArm64") {
        dependsOn(rustBuildTask)
    }
    getByName("compileKotlinLinuxArm64") {
        dependsOn(rustBuildTask)
    }
    getByName("compileKotlinLinuxX64") {
        dependsOn(rustBuildTask)
    }
}

