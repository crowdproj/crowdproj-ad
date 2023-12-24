plugins {
    id("com.crowdproj.plugin.autoversion") version "0.0.5"
    alias(libsfe.plugins.kotlin.jvm) apply false
    alias(libsfe.plugins.kotlin.multiplatform) apply false
    alias(libsfe.plugins.kotlin.android) apply false
    alias(libsfe.plugins.android.application) apply false
    alias(libsfe.plugins.android.library) apply false
    alias(libsfe.plugins.compose) apply false
}

group = "com.crowdproj.ad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

autoversion {
    shoudIncrement.set(false)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
