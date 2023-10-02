plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting  {
            dependencies {
//                implementation(project(":shared"))
                implementation(compose.ui)
            }
        }
    }
}

compose.experimental {
    web.application {}
}
