plugins {
    alias(libsfe.plugins.kotlin.multiplatform)
    alias(libsfe.plugins.compose)
}

kotlin {
    js {
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
