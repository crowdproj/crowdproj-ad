plugins {
    id("backend-convention")
}

version = rootProject.version

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":crowdproj-ad-common"))

                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))

                api(libs.coroutines.test)
                api(libs.coroutines.core)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
