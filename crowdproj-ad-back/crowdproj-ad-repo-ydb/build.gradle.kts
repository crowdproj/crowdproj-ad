plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

version = rootProject.version

//val goBuilds: Configuration by configurations.creating {
//    isCanBeConsumed = false
//    isCanBeResolved = true
//}

//dependencies {
//    goBuilds(project(":crowdproj-ad-go")) {
//        targetConfiguration = "goBuilds"
//    }
//}

kotlin {
    jvm { withJava() }
    linuxX64 {
        val main by compilations.getting

//        val golang by main.cinterops.creating {
//            val goLibsPath = "$buildDir/goFiles/linux-amd64"
////            packageName("${project.group}")
//            includeDirs {
//                allHeaders(files(goLibsPath))
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
    linuxArm64 {}

    sourceSets {
        val coroutinesVersion: String by project
        val uuidVersion: String by project
        val testContainersVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(project(":crowdproj-ad-common"))

                implementation(libs.kotlinx.serialization.core)
                implementation(libs.uuid)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":crowdproj-ad-repo-tests"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.testcontainers:postgresql:$testContainersVersion")
            }
        }

        val linuxX64Main by getting {
            dependencies {
//                implementation(project(":crowdproj-ad-go", "goBuilds"))
            }
        }
    }
}

//tasks {
//    val getGoBuilds by creating(Copy::class) {
//        from(goBuilds)
//        into("$buildDir/goFiles")
//    }
//    filter { it.name.startsWith("compile") }.forEach {
//        it.dependsOn(getGoBuilds)
//    }
//    filter { it.name.startsWith("cinterop") }.forEach {
//        it.dependsOn(getGoBuilds)
//    }
//}
