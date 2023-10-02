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
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("ch.qos.logback:logback-access:$logbackVersion")

                implementation("org.slf4j:slf4j-api:$slf4jVersion")
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
