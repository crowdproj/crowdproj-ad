dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.be.versions.toml"))
        }
    }
}

include("crowdproj-ad-docs-market")
