plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

group = "com.crowdproj.ad"
version = "0.0.1"

repositories {
    mavenCentral()
}

subprojects {
    this.group = group
    this.version = version

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
