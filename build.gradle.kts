plugins {
    id("com.crowdproj.plugin.autoversion") version "0.0.5"
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
}

group = "com.crowdproj.ad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

autoversion {
    shoudIncrement.set(false)
}
