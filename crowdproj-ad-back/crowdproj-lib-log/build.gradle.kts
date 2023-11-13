plugins {
    id("backend-convention")
}

version = rootProject.version

kotlin {
    sourceSets {
        val logbackVersion: String by project
        val slf4jVersion: String by project

        val commonMain by getting {

            dependencies {
                implementation(kotlin("stdlib-common"))
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
                implementation(libs.logback.classic)
                implementation(libs.logback.access)

                implementation(libs.slf4j)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val nativeMain by getting {
            dependencies {
                dependsOn(commonMain)
            }
        }
    }
}
