plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    id("org.ysb33r.terraform.wrapper")
}

group = "com.crowdproj.ad"
version = "0.0.1"

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
    @Suppress("UNUSED_VARIABLE")
    val deploy: Task by creating {
        dependsOn("build")
    }
}
