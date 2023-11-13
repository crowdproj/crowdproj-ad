plugins {
    id("backend-convention")
}

version = rootProject.version

kotlin {
    sourceSets {
        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(libs.cor)

                implementation(project(":crowdproj-ad-common"))
                implementation(project(":crowdproj-ad-stubs"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(project(":crowdproj-ad-repo-stubs"))
                implementation(project(":crowdproj-ad-repo-tests"))
                implementation(project(":crowdproj-ad-repo-inmemory"))

                api(libs.coroutines.test)
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
