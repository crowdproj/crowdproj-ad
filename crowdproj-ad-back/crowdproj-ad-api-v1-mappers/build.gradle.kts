plugins {
    id("backend-convention")
}

version = rootProject.version

kotlin {
    sourceSets {
        val commonMain by getting {

            dependencies {
                implementation(kotlin("stdlib-common"))

                api(project(":crowdproj-ad-api-v1"))
                implementation(project(":crowdproj-ad-common"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
