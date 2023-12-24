plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
//    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.crowdproj.generator) apply false
}

group = "com.crowdproj.ad"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}
