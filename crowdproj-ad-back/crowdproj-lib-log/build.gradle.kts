plugins {
    id("backend-convention")
}

version = rootProject.version

kotlin {
    sourceSets {
        val logbackVersion: String by project
        val slf4jVersion: String by project

        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.logback.classic)
                implementation(libs.logback.access)

                implementation(libs.slf4j)
            }
        }
        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        nativeMain {
        }
    }
}
