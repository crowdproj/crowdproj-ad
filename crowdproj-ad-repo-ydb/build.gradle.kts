plugins {
    kotlin("multiplatform")
}

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

    sourceSets {
        val coroutinesVersion: String by project
        val uuidVersion: String by project
        val testContainersVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(project(":crowdproj-ad-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("com.benasher44:uuid:$uuidVersion")

            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":crowdproj-ad-repo-tests"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.testcontainers:postgresql:$testContainersVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
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
