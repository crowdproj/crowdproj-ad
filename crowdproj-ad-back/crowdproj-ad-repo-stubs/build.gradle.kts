plugins {
    id("backend-convention")
}

version = rootProject.version

kotlin {
    sourceSets {
        val coroutinesVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(project(":crowdproj-ad-common"))
                implementation(project(":crowdproj-ad-stubs"))

                implementation(libs.coroutines.core)

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
