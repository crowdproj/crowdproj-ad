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
    compileSdk = 34

    defaultConfig {
        applicationId = "com.crowdproj.ad.front"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf("VERSION_${libs.versions.jvm.compiler.get()}")
        targetCompatibility = JavaVersion.valueOf("VERSION_${libs.versions.jvm.compiler.get()}")
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
//        jvmToolchain(17)
        jvmToolchain(libs.versions.jvm.compiler.get().toInt())
    }
//    buildFeatures {
////        viewBinding = true
//        compose = true
//    }
}

dependencies {
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.preview)

    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    implementation(compose.components.resources)

    api("androidx.activity:activity-compose:1.7.2")
    api("androidx.appcompat:appcompat:1.6.1")
    api("androidx.core:core-ktx:1.10.1")
    implementation("androidx.camera:camera-camera2:1.2.3")
    implementation("androidx.camera:camera-lifecycle:1.2.3")
    implementation("androidx.camera:camera-view:1.2.3")
    implementation("com.google.accompanist:accompanist-permissions:0.29.2-rc")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.maps.android:maps-compose:2.11.2")
}
