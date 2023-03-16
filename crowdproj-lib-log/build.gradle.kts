plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm { withJava() }
    linuxX64 { }

    sourceSets {
        val logbackVersion: String by project
        val slf4jVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {

            dependencies {
                implementation(kotlin("stdlib-common"))
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
        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("ch.qos.logback:logback-access:$logbackVersion")

                implementation("org.slf4j:slf4j-api:$slf4jVersion")
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
