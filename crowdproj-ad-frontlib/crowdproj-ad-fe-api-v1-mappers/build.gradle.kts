plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

version = rootProject.version

kotlin {
    jvm()
    linuxX64()
    linuxArm64()

    sourceSets {
        val commonMain by getting {

            dependencies {
                implementation(kotlin("stdlib-common"))

                api("com.crowdproj.ad:crowdproj-ad-api-v1:${version}")
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
