plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

version = rootProject.version

kotlin {
    jvm()
    js {
        browser {}
    }
//    linuxX64()
//    linuxArm64()

    sourceSets {
        val commonMain by getting {

            dependencies {
                implementation(kotlin("stdlib-common"))

                api(project(":crowdproj-ad-api-v1"))
                implementation(project(":crowdproj-ad-fe-common"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit"))
//            }
//        }
    }
}
