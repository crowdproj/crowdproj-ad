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

    plugins.withId("org.jetbrains.kotlin.jvm") {
        val implementation by configurations
        val testImplementation by configurations
        dependencies {
            implementation(kotlin("stdlib"))

            testImplementation(kotlin("test-junit"))
        }
    }
}

