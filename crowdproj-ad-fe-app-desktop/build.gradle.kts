plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    jvm {}
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.foundation)
                implementation(compose.material)
//                implementation(project(":shared"))

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
