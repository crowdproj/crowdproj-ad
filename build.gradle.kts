plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
//    id("org.ysb33r.terraform.wrapper") version "1.0.0"
    id("com.crowdproj.plugin.autoversion")
}

group = "com.crowdproj.ad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

autoversion {
    shoudIncrement.set(false)
}

subprojects {
    this.group = rootProject.group
    this.version = rootProject.version

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

afterEvaluate {
    val deploy: Task by tasks.creating {
        println("VER: ${project.version}")
        group = "build"
        dependsOn("build")
        dependsOn(project(":crowdproj-ad-app-ktor").getTasksByName("deploy",false))
    }
}

afterEvaluate {
    println("VERSION: ${project.version}")
}
