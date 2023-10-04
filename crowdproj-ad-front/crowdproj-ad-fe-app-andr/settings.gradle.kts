dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.google.com/")
    }
//
//    plugins {
////        val kotlinVersion = extra["kotlin.version"] as String
////        val agpVersion = extra["agp.version"] as String
////        val composeVersion = extra["compose.version"] as String
////
//////        kotlin("jvm").version(kotlinVersion)
//////        kotlin("multiplatform").version(kotlinVersion)
//////        kotlin("plugin.serialization").version(kotlinVersion)
////        kotlin("android").version(kotlinVersion)
////        id("com.android.base").version(agpVersion)
////        id("com.android.application").version(agpVersion)
////        id("com.android.library").version(agpVersion)
////        id("org.jetbrains.compose").version(composeVersion)
//    }
}

//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version("0.4.0")
//}

rootProject.name = "crowdproj-ad-front"
