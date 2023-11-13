plugins {
    id("backend-convention")
}

version = rootProject.version

kotlin {
    sourceSets {
        val cache4kVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(project(":crowdproj-ad-common"))

                implementation(libs.cache4k)
                implementation(libs.coroutines.core)
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
            }
        }
    }
}
