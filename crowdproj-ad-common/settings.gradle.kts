rootProject.name = "crowdproj-ad-common"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include("crowdproj-ad-api-v1")
