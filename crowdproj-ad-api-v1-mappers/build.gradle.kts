plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm { }
    linuxX64 { }

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {

            kotlin.srcDirs("$buildDir/generate-resources/main/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":crowdproj-ad-api-v1"))
                implementation(project(":crowdproj-ad-common"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
