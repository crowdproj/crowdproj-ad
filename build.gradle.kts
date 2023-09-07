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

subprojects {
    this.group = rootProject.group
    this.version = rootProject.version

    repositories {
        mavenCentral()
    }
}

tasks {
    val deploy: Task by creating {
        dependsOn("build")
    }
}
