plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

group = "com.crowdproj.ad"
version = "0.0.4"

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
