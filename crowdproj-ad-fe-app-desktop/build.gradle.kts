plugins {
    alias(libsfe.plugins.kotlin.multiplatform)
    alias(libsfe.plugins.compose)
}

kotlin {
    jvm {}
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.foundation)
                implementation(compose.material)

                implementation("com.crowdproj.ad.front:crowdproj-ad-fe-views:${version}")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}
