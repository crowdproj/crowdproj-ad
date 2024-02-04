dependencyResolutionManagement {
    versionCatalogs {
        create("baselibs") {
            from(files("../gradle/libs.versions.toml"))
        }
        create("libs") {
            from(files("../gradle/libs.be.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../backend-convention")
    plugins {
        id("backend-convention") version "1.0.0" apply false
    }
}

include("lib-testcontainers")

include("crowdproj-lib-log")

include("crowdproj-ad-common")
include("crowdproj-ad-api-v1")
include("crowdproj-ad-api-v1-mappers")
include("crowdproj-ad-app-ktor")
include("crowdproj-ad-app-swagger")
include("crowdproj-ad-stubs")
include("crowdproj-ad-biz")

include("crowdproj-ad-repo-stubs")
include("crowdproj-ad-repo-tests")
include("crowdproj-ad-repo-inmemory")
include("crowdproj-ad-repo-gremlin")

//include("crowdproj-ad-repo-ydb")
