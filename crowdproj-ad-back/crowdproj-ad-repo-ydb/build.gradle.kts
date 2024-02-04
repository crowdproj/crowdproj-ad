plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm { withJava() }
    linuxX64 {
        compilations.getByName("main").cinterops {
            create("rs_ydb_conn") {}
        }
    }
    linuxArm64 {
        compilations.getByName("main").cinterops {
            create("rs_ydb_conn") {}
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":crowdproj-ad-common"))

                implementation(libs.kotlinx.serialization.core)
                implementation(libs.uuid)

            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":crowdproj-ad-repo-tests"))
            }
        }

        jvmMain {
            dependencies {
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.test.containers)
            }
        }

        val linuxX64Main by getting {
            dependencies {
            }
        }
    }
}
