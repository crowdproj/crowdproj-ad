plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
//                implementation(project(":shared"))
            }
        }
    }
}
android {
    namespace = "com.crowdproj.ad.front"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "com.crowdproj.ad.front"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.compile.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf("VERSION_${libs.versions.jvm.language.get()}")
        targetCompatibility = JavaVersion.valueOf("VERSION_${libs.versions.jvm.compiler.get()}")
    }
    kotlin {
        jvmToolchain(libs.versions.jvm.compiler.get().toInt())
    }
}

dependencies {
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.preview)

    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    implementation(compose.components.resources)

    api(libs.compose.activity)
    api(libs.androidx.appcompat)
    api(libs.androidx.core)
//    implementation("com.google.accompanist:accompanist-permissions:0.29.2-rc")
//    implementation("com.google.android.gms:play-services-maps:18.1.0")
//    implementation("com.google.android.gms:play-services-location:21.0.1")
//    implementation("com.google.maps.android:maps-compose:2.11.2")

    implementation("com.crowdproj.ad.front:crowdproj-ad-fe-views:${version}")
}
