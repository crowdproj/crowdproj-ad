rootProject.name = "crowdproj-ad"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val ktorPluginVersion: String by settings
//        val openapiVersion: String by settings
        val codeGeneratorVersion: String by settings
        val bmuschkoVersion: String by settings
        val graalvmPluginVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false

//        id("org.openapi.generator") version openapiVersion apply false
        id("com.crowdproj.generator") version codeGeneratorVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
        id("org.graalvm.buildtools.native") version graalvmPluginVersion
    }
}
include("crowdproj-lib-log")

include("crowdproj-ad-common")
include("crowdproj-ad-api-v1")
include("crowdproj-ad-api-v1-mappers")
include("crowdproj-ad-app-ktor")
include("crowdproj-ad-app-ktor")
include("crowdproj-ad-app-swagger")
