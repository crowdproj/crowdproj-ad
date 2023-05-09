import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

plugins {
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    id("io.ktor.plugin")
    id("com.bmuschko.docker-remote-api")
    id("org.ysb33r.terraform")
    id("org.ysb33r.terraform.remotestate.s3")
}

val ktorVersion: String by project
val serializationVersion: String by project
val datetimeVersion: String by project
val coroutinesVersion: String by project
val logbackVersion: String by project
val slf4jVersion: String by project
val uuidVersion: String by project

fun ktorServer(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-server-$module:$version"

fun ktorClient(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-client-$module:$version"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

kotlin {
    jvm { withJava() }
    linuxX64 {
        binaries {
            executable {
                baseName = project.name
                entryPoint = "com.crowdproj.ad.app.main"
            }
        }
    }

    sourceSets {

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-serialization:$ktorVersion")

                implementation(ktorClient("core"))
                implementation(ktorClient("cio"))

                implementation(ktorServer("content-negotiation"))
                implementation(ktorServer("auto-head-response"))
                implementation(ktorServer("caching-headers"))
                implementation(ktorServer("cors"))
                implementation(ktorServer("call-id"))
                //implementation(ktorServer("websockets"))
                implementation(ktorServer("config-yaml"))
                implementation(ktorServer("core"))
                implementation(ktorServer("cio"))
                implementation(ktorServer("auth"))

                implementation("com.benasher44:uuid:$uuidVersion")

                implementation(project(":crowdproj-lib-log"))

                implementation(project(":crowdproj-ad-common"))

                implementation(project(":crowdproj-ad-api-v1"))
                implementation(project(":crowdproj-ad-api-v1-mappers"))
                implementation(project(":crowdproj-ad-biz"))
                implementation(project(":crowdproj-ad-repo-inmemory"))
                implementation(project(":crowdproj-ad-repo-stubs"))

            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(ktorServer("test-host"))
                implementation(ktorClient("content-negotiation"))
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("ch.qos.logback:logback-access:$logbackVersion")

                implementation("org.slf4j:slf4j-api:$slf4jVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val linuxX64Main by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val linuxX64Test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

ktor {
    docker {
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        localImageName.set(project.name)
        imageTag.set("${project.version}")
        portMappings.set(
            listOf(
                io.ktor.plugin.features.DockerPortMapping(
                    80,
                    8080,
                    io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                )
            )
        )

//        externalRegistry.set(
//            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
//                appName = provider { "ktor-app" },
//                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
//                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
//            )
//        )
    }

}

terraform {
    executable(mapOf("version" to "1.4.6"))
    variables {
//        `var`("region", awsRegions)
//        `var`("domainZone", apiDomain)
//        `var`("domain", "$serviceAlias.$apiDomain")
//        `var`("bucketJarName", "$awsBucket.$serviceAlias")
//        `var`("bucketBackend", awsBucketPrivate)
//        `var`("handlerJar", tasks.shadowJar.get().archiveFile.get().asFile.absoluteFile)
//        `var`("parametersPrefix", paramsPrefix)
//        `var`("parameterCorsOrigins", paramCorsOrigins)
//        `var`("parameterCorsHeaders", paramCorsHeaders)
//        `var`("parameterCorsMethods", paramCorsMethods)
//        `var`("parameterNeptuneEndpoint", parameterNeptuneEndpoint)
//        `var`("parameterNeptunePort", parameterNeptunePort)
//        map(mapOf<String, String>(
//            "teams-create" to "com.crowdproj.aws.handlers.TeamsCreateHandler::handleRequest",
//            "teams-update" to "com.crowdproj.aws.handlers.TeamsUpdateHandler::handleRequest",
//            "teams-index" to "com.crowdproj.aws.handlers.TeamsIndexHandler::handleRequest",
//            "teams-get" to "com.crowdproj.aws.handlers.TeamsGetHandler::handleRequest"
//        ), "handlers")
//        `var`("handler", "com.crowdproj.aws.base.TeamsApiGatewayHandler::handleRequest")
//        list("corsOrigins",
//            "https://$domainPublic"
//        )
//        list("corsHeaders",
//            "*",
//            "Content-Type",
//            "X-Amz-Date",
//            "Authorization",
//            "X-Api-Key",
//            "X-Amz-Security-Token",
//            "X-Requested-With"
//        )
//        list("corsMethods",
//            "OPTIONS", "POST"
//        )
////        `var`("stateTable", "arn:aws:dynamodb:us-east-1:709565996550:table/com.crowdproj.states")
////        `var`("health_check_alarm_sns_topics", "crowdproj-public-website-alarm")
    }
    remote {
//        setPrefix("states-$apiVersion/state-teams")
        s3 {
//            setRegion(awsRegions)
//            setBucket(awsBucketState)
        }
    }
}

tasks {
    val linkReleaseExecutableLinuxX64 by getting(KotlinNativeLink::class)
    val nativeFile = linkReleaseExecutableLinuxX64.binary.outputFile
//    val linkDebugExecutableLinuxX64 by getting(KotlinNativeLink::class)
//    val nativeFile = linkDebugExecutableLinuxX64.binary.outputFile
    val linuxX64ProcessResources by getting(ProcessResources::class)

    val dockerDockerfile by creating(Dockerfile::class) {
        dependsOn(linkReleaseExecutableLinuxX64)
        dependsOn(linuxX64ProcessResources)
        group = "docker"
        from("ubuntu:22.04")
        doFirst {
            copy {
                from(nativeFile)
                from(linuxX64ProcessResources.destinationDir)
                into("${this@creating.destDir.get()}")
            }
        }
        copyFile(nativeFile.name, "/app/")
        copyFile("application.yaml", "/app/")
        exposePort(8081)
        workingDir("/app")
        entryPoint("/app/${nativeFile.name}", "-config=./application.yaml")
    }
    val registryUser: String? = System.getenv("CONTAINER_REGISTRY_USER")
    val registryPass: String? = System.getenv("CONTAINER_REGISTRY_PASS")
    val registryHost: String? = System.getenv("CONTAINER_REGISTRY_HOST")
    val registryPref: String? = System.getenv("CONTAINER_REGISTRY_PREF")
    val imageName = registryPref?.let { "$it/${project.name}" } ?: project.name.toString()

    val dockerBuildNativeImage by creating(DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerDockerfile)
        images.add("$imageName:${project.version}")
        images.add("$imageName:latest")
    }
    val dockerPushNativeImage by creating(DockerPushImage::class) {
        group = "docker"
        dependsOn(dockerBuildNativeImage)
        images.set(dockerBuildNativeImage.images)
        registryCredentials {
            username.set(registryUser)
            password.set(registryPass)
            url.set("https://$registryHost/v1/")
        }
    }

    create("deploy") {
        group = "build"
        dependsOn(dockerPushNativeImage)
    }
}
