plugins {
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    id("io.ktor.plugin")
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
                baseName = rootProject.name
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

    }
}

ktor {
//    fatJar {
//        archiveFileName.set("fat.jar")
//    }

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
