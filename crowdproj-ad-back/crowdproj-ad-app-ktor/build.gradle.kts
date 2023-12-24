@file:Suppress("UnstableApiUsage")

import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

plugins {
    id("backend-convention")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.muschko.remote.api)
    application
}

version = rootProject.version

application {
//    mainClass.set("io.ktor.server.cio.EngineMain")
    mainClass.set("com.crowdproj.ad.app.ApplicationJvmKt")
}

kotlin {
    jvm {
        withJava()
    }
    linuxX64 {
        binaries {
            executable {
                baseName = "${project.name}-x64"
                entryPoint = "com.crowdproj.ad.app.main"
            }
        }
    }
    linuxArm64 {
        binaries {
            executable {
                baseName = "${project.name}-arm64"
                entryPoint = "com.crowdproj.ad.app.main"
            }
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api(libs.datetime)
                implementation(libs.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.serialization.kotlinx.json)

                implementation(libs.ktor.server.content.negotiation)
                implementation(libs.ktor.server.auto.head.response)
                implementation(libs.ktor.server.caching.headers)
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.cio)
                implementation(libs.ktor.server.cors)
                implementation(libs.ktor.server.auth)
                implementation(libs.ktor.server.call.id)
                implementation(libs.ktor.server.config.yaml)

                implementation(libs.uuid)

                implementation(project(":crowdproj-lib-log"))

                implementation(project(":crowdproj-ad-common"))

                implementation(project(":crowdproj-ad-api-v1-mappers"))
                implementation(project(":crowdproj-ad-biz"))
                implementation(project(":crowdproj-ad-repo-inmemory"))
                implementation(project(":crowdproj-ad-repo-stubs"))
                implementation(project(":crowdproj-ad-app-swagger"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.ktor.server.test.host)
                implementation(libs.ktor.client.content.negotiation)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)

                implementation(libs.ktor.client.mock)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.logback.classic)
                implementation(libs.logback.access)

                implementation(libs.slf4j)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val nativeMain by getting {
            dependsOn(commonMain)
//            dependencies {
//            }
        }

        val linuxX64Main by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }

        val linuxX64Test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val linuxArm64Main by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val linuxArm64Test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

    }
}

tasks {
    val linkReleaseExecutableLinuxX64 by getting(KotlinNativeLink::class)
    val nativeFileX64 = linkReleaseExecutableLinuxX64.binary.outputFile
    val linkReleaseExecutableLinuxArm64 by getting(KotlinNativeLink::class)
    val nativeFileArm64 = linkReleaseExecutableLinuxArm64.binary.outputFile
//    val linkDebugExecutableLinuxX64 by getting(KotlinNativeLink::class)
//    val nativeFile = linkDebugExecutableLinuxX64.binary.outputFile
    val linuxX64ProcessResources by getting(ProcessResources::class)
    val linuxArm64ProcessResources by getting(ProcessResources::class)
    val dockerLinuxX64Dir = layout.buildDirectory.file("docker-x64/Dockerfile").get().asFile
    val dockerLinuxArm64Dir = layout.buildDirectory.file("docker-arm64/Dockerfile").get().asFile

    val dockerDockerfileX64 by creating(Dockerfile::class) {
        dependsOn(linkReleaseExecutableLinuxX64)
        dependsOn(linuxX64ProcessResources)
        group = "docker"
        destFile.set(dockerLinuxX64Dir)
        from(Dockerfile.From("ubuntu:23.04").withPlatform("linux/amd64"))
        doFirst {
            copy {
                from(nativeFileX64)
                from(linuxX64ProcessResources.destinationDir)
                into("${this@creating.destDir.get()}")
            }
        }
        copyFile(nativeFileX64.name, "/app/")
        copyFile("application.yaml", "/app/")
        exposePort(8081)
        workingDir("/app")
        entryPoint("/app/${nativeFileX64.name}", "-config=./application.yaml")
    }
    val dockerDockerfileArm64 by creating(Dockerfile::class) {
        dependsOn(linkReleaseExecutableLinuxArm64)
        dependsOn(linuxArm64ProcessResources)
        group = "docker"
        destFile.set(dockerLinuxArm64Dir)
        from(Dockerfile.From("ubuntu:23.04").withPlatform("linux/arm64"))
        doFirst {
            copy {
                from(nativeFileArm64)
                from(linuxArm64ProcessResources.destinationDir)
                into("${this@creating.destDir.get()}")
            }
        }
        copyFile(nativeFileArm64.name, "/app/")
        copyFile("application.yaml", "/app/")
        exposePort(8081)
        workingDir("/app")
        entryPoint("/app/${nativeFileArm64.name}", "-config=./application.yaml")
    }
    val registryUser: String? = System.getenv("CONTAINER_REGISTRY_USER")
    val registryPass: String? = System.getenv("CONTAINER_REGISTRY_PASS")
    val registryHost: String? = System.getenv("CONTAINER_REGISTRY_HOST")
    val registryPref: String? = System.getenv("CONTAINER_REGISTRY_PREF")
    val imageName = registryPref?.let { "$it/${project.name}" } ?: project.name

    val dockerBuildX64Image by creating(DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerDockerfileX64)
        inputDir.set(dockerLinuxX64Dir.parentFile)
        images.add("$imageName-x64:${rootProject.version}")
        images.add("$imageName-x64:latest")
        platform.set("linux/amd64")
    }
    val dockerPushX64Image by creating(DockerPushImage::class) {
        group = "docker"
        dependsOn(dockerBuildX64Image)
        images.set(dockerBuildX64Image.images)
        registryCredentials {
            username.set(registryUser)
            password.set(registryPass)
            url.set("https://$registryHost/v1/")
        }
    }

    val dockerBuildArm64Image by creating(DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerDockerfileArm64)
        inputDir.set(dockerLinuxArm64Dir.parentFile)
        images.add("$imageName-arm64:${rootProject.version}")
        images.add("$imageName-arm64:latest")
        platform.set("linux/arm64")
    }
    val dockerPushArm64Image by creating(DockerPushImage::class) {
        group = "docker"
        dependsOn(dockerBuildArm64Image)
        images.set(dockerBuildArm64Image.images)
        registryCredentials {
            username.set(registryUser)
            password.set(registryPass)
            url.set("https://$registryHost/v1/")
        }
    }

    create("deploy") {
        group = "build"
        dependsOn(dockerPushX64Image)
        dependsOn(dockerPushArm64Image)
    }
}
