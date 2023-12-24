dependencyResolutionManagement {
    versionCatalogs {
        create("baselibs") {
            from(files("../gradle/libs.versions.toml"))
        }
        create("libs") {
            from(files("../gradle/libs.fe.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }
}

include(":crowdproj-ad-api-v1")
include(":crowdproj-ad-fe-api-v1-mappers")
include(":crowdproj-ad-fe-common")
include(":crowdproj-ad-fe-views")
