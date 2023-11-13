plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting  {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)

                implementation("com.crowdproj.ad.front:crowdproj-ad-fe-views:${version}")
            }
        }
    }
}

compose.experimental {
    web.application {}
}
