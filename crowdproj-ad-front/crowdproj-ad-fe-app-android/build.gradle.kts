plugins {
//    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
//    alias(libs.plugins.compose)
//    id("com.android.application")
//    id("org.jetbrains.compose")
}

kotlin {
//    jvm {  }
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
//                implementation(project(":shared"))
            }
        }

        val androidUnitTest by getting {
            dependencies {

            }
        }
        val androidInstrumentedTest by getting {
            dependencies {

            }
        }
    }
}

android {
    compileSdk = 34
    namespace = "org.jetbrains.fallingballs"
    defaultConfig {
        applicationId = "org.jetbrains.FallingBalls"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
