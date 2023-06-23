rootProject.name = "crowdproj-ad"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val ktorPluginVersion: String by settings
        val codeGeneratorVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false

        id("com.crowdproj.generator") version codeGeneratorVersion apply false

//        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}
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
include("crowdproj-ad-repo-ydb")
include("crowdproj-ad-go")
