rootProject.name = "backend-convention"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.be.versions.toml"))
        }
    }
}
