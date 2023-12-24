rootProject.name = "crowdproj-ad"

dependencyResolutionManagement {
    versionCatalogs {
        create("libsbe") {
            from(files("./gradle/libs.be.versions.toml"))
        }
        create("libsfe") {
            from(files("./gradle/libs.fe.versions.toml"))
        }
    }
}

includeBuild("crowdproj-ad-docs")
includeBuild("crowdproj-ad-back")
includeBuild("crowdproj-ad-frontlib")

include(":crowdproj-ad-fe-app-andr")
include(":crowdproj-ad-fe-app-js")
include(":crowdproj-ad-fe-app-desktop")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.google.com/")
    }
}
